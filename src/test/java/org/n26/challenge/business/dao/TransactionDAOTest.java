package org.n26.challenge.business.dao;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.n26.challenge.business.util.DateUtil;
import org.n26.challenge.persistence.entity.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionDAOTest {

    /**
     * Get this value from the application.yml file
     */
    @Value("${statisticService.windowInSec}")
    private int windowInSec;

    @Autowired
    private TransactionDAO transactionDAO;

    @Test
    public void updateTransactions_success() {
        Statistic[] transactions = new Statistic[windowInSec];
        transactions[5] = new Statistic(4.0, 3.0, 2.0, 1.0, 1);
        transactionDAO.updateTransactionsStatistics(transactions);
        Assert.assertEquals(transactionDAO.getTransactionsStatistics().length, windowInSec);
        Assert.assertEquals(transactionDAO.getTransactionsStatistics()[5], transactions[5]);
    }
    
    @Test
    public void updateLastInsertTimestamp_success() {
        long lastInsertTimestamp = DateUtil.converToTimeStamp(LocalDateTime.now());
        transactionDAO.updateLastInsertTimestamp(lastInsertTimestamp);;
        Assert.assertEquals(transactionDAO.getLastInsertTimestamp(), lastInsertTimestamp);
    }
}
