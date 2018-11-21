package org.n26.challenge.business.dao;

import org.n26.challenge.persistence.entity.Statistic;

/**
 * Helping in storing and fetching lastInsertTimestamp and statistics for last 60 second.
 * 
 * @author manjit kumar
 * 
 */
public interface TransactionDAO {
    /**
     * This method return timestamp of last transaction.
     * 
     * @return timestamp of last transaction.
     */
    public long getLastInsertTimestamp();

    /**
     * This method updating timestamp of last transaction.
     * 
     * @param lastInsertTimestamp timestamp of last transaction.
     */
    public void updateLastInsertTimestamp(long lastInsertTimestamp);

    /**
     * This method return the statistics of transactions
     * 
     * @return transactions statistics
     */
    public Statistic[] getTransactionsStatistics();

    /**
     * This method is updating the statistics of transactions
     * 
     * @param transactionsStatistics statistics of transactions
     */
    public void updateTransactionsStatistics(Statistic[] transactionsStatistics);
}
