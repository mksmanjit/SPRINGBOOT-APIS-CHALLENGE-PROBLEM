package org.n26.challenge.business.service;

import org.n26.challenge.persistence.entity.Statistic;

/**
 * Helping in consolidating statistics of transactions happened in given interval.
 * 
 * @author manjit kumar
 * 
 */
public interface StatisticService {

    /**
     * This method is consolidating statistics of transactions happened in given interval.
     * 
     * Time complexity for this method is O(1) means fixed, irrespective of data.
     * 
     * @return consolidating statistics happened in given interval.
     */
    public Statistic getStatistics();

}
