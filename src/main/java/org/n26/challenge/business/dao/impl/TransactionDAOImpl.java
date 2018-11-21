package org.n26.challenge.business.dao.impl;

import org.n26.challenge.business.dao.TransactionDAO;
import org.n26.challenge.persistence.entity.Statistic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionDAOImpl implements TransactionDAO {

    /**
     * Get this value from the application.yml file
     */
    @Value("${statisticService.windowInSec}")
    private int windowInSec;

    /**
     * time the last transaction added.
     */
    private long lastInsertTimestamp;

    /**
     * Holds the statistics of transaction of given interval.
     */
    private Statistic[] transactionsStatistics = new Statistic[windowInSec];

    @Override
    public long getLastInsertTimestamp() {
        return lastInsertTimestamp;
    }

    @Override
    public void updateLastInsertTimestamp(long lastInsertTimestamp) {
        this.lastInsertTimestamp = lastInsertTimestamp;
    }

    @Override
    public Statistic[] getTransactionsStatistics() {
        return transactionsStatistics;
    }

    @Override
    public void updateTransactionsStatistics(Statistic[] transactions) {
        this.transactionsStatistics = transactions;
    }

}
