package org.n26.challenge.business.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.n26.challenge.business.dao.TransactionDAO;
import org.n26.challenge.business.service.TransactionService;
import org.n26.challenge.business.util.DateUtil;
import org.n26.challenge.exception.TransactionExpiredException;
import org.n26.challenge.persistence.entity.Statistic;
import org.n26.challenge.persistence.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    /**
     * Get this value from the application.yml file
     */
    @Value("${statisticService.windowInSec}")
    private int windowInSec;

    @Autowired
    private TransactionDAO transactionDAO;

    /**
     * Used in locking the transaction when other user is updating/adding.
     */
    Object LOCK = new Object();

    @Override
    public void add(Transaction transaction) throws TransactionExpiredException {
        Long currentTimestamp = DateUtil.converToTimeStamp(LocalDateTime.now());
        Long transactionTimestamp = transaction.getTimestamp();
        if (transactionTimestamp + windowInSec * DateUtil.ONE_MILLIS <= currentTimestamp) {
            throw new TransactionExpiredException();
        }
        // Lock the transaction so handle multiple request will not cause concurrency issues.
        synchronized (LOCK) {
            long lastInsertTimestamp = transactionDAO.getLastInsertTimestamp();
            Statistic[] oldTransactionsStatistics = transactionDAO.getTransactionsStatistics();
            // rotationCount means seconds passed after last insertion, so that we remove the stale data.
            int rotationCount = (int) DateUtil.getTimeDiffInSeconds(lastInsertTimestamp, currentTimestamp);
            // index means position on which we need to add data.
            int index = (int) DateUtil.getTimeDiffInSeconds(transactionTimestamp, currentTimestamp);
            Statistic[] upatedTransactionsStatistics = removeStaleData(oldTransactionsStatistics, rotationCount);
            updateStatisticsAtGivenIndex(transaction, index, upatedTransactionsStatistics);
            // reset with fresh data.
            transactionDAO.updateTransactionsStatistics(upatedTransactionsStatistics);
            transactionDAO.updateLastInsertTimestamp(currentTimestamp);
        }
    }

    /**
     * This method is updating the transactionsStatistics at given index.
     * 
     * @param transaction holds the data for given transaction.
     * @param index indicate on which index you have to update the statistics.
     * @param transactionsStatistics object which we are going to update and it holds statistics data.
     */
    private void updateStatisticsAtGivenIndex(Transaction transaction, int index,
            Statistic[] transactionsStatistics) {
        Statistic statistic;
        if (transactionsStatistics[index] == null) {
            double amount = transaction.getAmount();
            statistic = new Statistic(amount, amount, amount, amount, 1);
        } else {
            statistic = transactionsStatistics[index];
            statistic.setSum(Double.sum(statistic.getSum(), transaction.getAmount()));
            statistic.setCount(statistic.getCount() + 1);
            statistic.setMax(Double.max(statistic.getMax(), transaction.getAmount()));
            statistic.setMin(Double.min(statistic.getMin(), transaction.getAmount()));
            statistic.setAvg(statistic.getSum() / statistic.getCount());
        }
        transactionsStatistics[index] = statistic;
    }

    /**
     * This is moving the statistics data if rotationCount is greater than zero and if rotationCount
     * is greater than or equal to windowInSec then create a fresh transactionsStatistics.
     * 
     * We are moving data by creating a new array and inserting data on newIndex(index + rotationCount),
     * this helps in setting those value which are valid rest will automatically set to null.
     * 
     * @param transactionsStatistics holds the statistics of transaction from which we are going to remove stale data.
     * @param rotationCount rotationCount means seconds passed after last insertion, so that we remove the stale data.
     *  
     * @return return the fresh transactionsStatistics.
     */
    private Statistic[] removeStaleData(Statistic[] transactionsStatistics, int rotationCount) {
        Statistic[] upatedTransactionsStatistics = transactionsStatistics;
        if (rotationCount >= windowInSec) {
            upatedTransactionsStatistics = new Statistic[windowInSec];
        } else if (rotationCount > 0) {
            upatedTransactionsStatistics = new Statistic[windowInSec];
            for (int i = 0; i < transactionsStatistics.length; i++) {
                int newIndex = i + rotationCount;
                if (newIndex < windowInSec)
                    upatedTransactionsStatistics[newIndex] = transactionsStatistics[i];
            }
        }
        return upatedTransactionsStatistics;
    }
}
