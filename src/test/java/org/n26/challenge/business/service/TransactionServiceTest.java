package org.n26.challenge.business.service;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.n26.challenge.business.dao.TransactionDAO;
import org.n26.challenge.business.service.TransactionService;
import org.n26.challenge.business.util.DateUtil;
import org.n26.challenge.exception.TransactionExpiredException;
import org.n26.challenge.persistence.entity.Statistic;
import org.n26.challenge.persistence.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    /**
     * Get this value from the application.yml file
     */
    @Value("${statisticService.windowInSec}")
    private int windowInSec;

    @Autowired
    private TransactionDAO transactionDAO;

    @Test(expected = TransactionExpiredException.class)
    public void addExpired() throws TransactionExpiredException {

        Transaction transaction = new Transaction();
        transaction.setAmount(6.0);
        transaction.setTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now().minusSeconds(windowInSec)));

        this.transactionService.add(transaction);
    }

    @Test
    public void add_upperBoundCheck() throws TransactionExpiredException {
        transactionDAO.updateLastInsertTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now().minusSeconds(windowInSec * 2)));

        Transaction transaction = new Transaction();
        transaction.setAmount(6.0);
        transaction.setTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now().minusSeconds(windowInSec)) + DateUtil.ONE_MILLIS / 2);

        transactionService.add(transaction);
        Assert.assertEquals(transactionDAO.getTransactionsStatistics()[windowInSec - 1], new Statistic(6.0, 6.0, 6.0, 6.0, 1));
        
        
    }

    @Test
    public void add_LowerBoundryCheck() throws TransactionExpiredException {
        transactionDAO.updateLastInsertTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now().minusSeconds(windowInSec * 2)));
        
        Transaction transaction = new Transaction();
        transaction.setAmount(6.0);
        transaction.setTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now()));

        transactionService.add(transaction);
        Assert.assertEquals(transactionDAO.getTransactionsStatistics()[windowInSec - windowInSec], new Statistic(6.0, 6.0, 6.0, 6.0, 1));
    }
    
    @Test
    public void add_addDataWithinSameSecond() throws TransactionExpiredException {
        transactionDAO.updateLastInsertTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now().minusSeconds(windowInSec * 2)));
        
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(6.0);
        transaction1.setTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now()));
        
        Transaction transaction2 = new Transaction();
        transaction2.setAmount(3.0);
        transaction2.setTimestamp(transaction1.getTimestamp());

        transactionService.add(transaction1);
        transactionService.add(transaction2);
        Assert.assertEquals(transactionDAO.getTransactionsStatistics()[windowInSec - windowInSec], new Statistic(9.0, 4.5, 6.0, 3.0, 2));
    }
    
    @Test
    public void add_verifyStaleDataRemoval() throws TransactionExpiredException, InterruptedException {
        transactionDAO.updateLastInsertTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now().minusSeconds(windowInSec * 2)));
        
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(6.0);
        transaction1.setTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now()));
        
        transactionService.add(transaction1);
        Assert.assertEquals(transactionDAO.getTransactionsStatistics()[windowInSec - windowInSec], new Statistic(6.0, 6.0, 6.0, 6.0, 1));
        
        Thread.sleep(DateUtil.ONE_MILLIS + 1);
        
        Transaction transaction2 = new Transaction();
        transaction2.setAmount(6.0);
        transaction2.setTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now()));
        
        transactionService.add(transaction2);
        
        Assert.assertEquals(transactionDAO.getTransactionsStatistics()[windowInSec - windowInSec + 1], new Statistic(6.0, 6.0, 6.0, 6.0, 1));
        Assert.assertEquals(transactionDAO.getTransactionsStatistics()[windowInSec - windowInSec], new Statistic(6.0, 6.0, 6.0, 6.0, 1));
    }

}
