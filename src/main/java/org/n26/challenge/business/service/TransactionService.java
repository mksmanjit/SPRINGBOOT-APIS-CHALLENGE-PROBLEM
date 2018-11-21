package org.n26.challenge.business.service;

import org.n26.challenge.exception.TransactionExpiredException;
import org.n26.challenge.persistence.entity.Transaction;

/**
 * Helping in storing/maintaining of statistics of transactions for given interval. Handling the concurrency issue while
 * doing the modification. Removing the stale data.
 * 
 * @author manjit kumar
 * 
 */
public interface TransactionService {

    /**
     * This method is calculating and maintain the statistics of transaction and if statistics is older than given
     * interval than remove stale data. Concurrency is taken care while updating/modifying statistics.
     * 
     * It takes constant memory O(1) and time O(1), irrespective of data.
     * 
     * @param transaction containing amount and timestamp.
     * @throws TransactionExpiredException If transaction timestamp is older than given interval.
     */
    public void add(Transaction transaction) throws TransactionExpiredException;

}
