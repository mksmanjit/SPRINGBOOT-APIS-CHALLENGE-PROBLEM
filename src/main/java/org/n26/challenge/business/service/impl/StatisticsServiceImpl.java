package org.n26.challenge.business.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.n26.challenge.business.dao.TransactionDAO;
import org.n26.challenge.business.service.StatisticService;
import org.n26.challenge.business.util.DateUtil;
import org.n26.challenge.persistence.entity.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticService {

   // @Autowired
    private TransactionDAO transactionDAO;
    
    @Autowired
    public StatisticsServiceImpl(TransactionDAO transactionDAO, @Value("20")Integer b) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public Statistic getStatistics() {
        long lastInsertTimestamp = transactionDAO.getLastInsertTimestamp();
        Statistic[] transactionsStatistics = transactionDAO.getTransactionsStatistics();
        long currentTimestamp = DateUtil.converToTimeStamp(LocalDateTime.now());
        // rotationCount means seconds passed after last insertion, so that we remove the stale data.
        int rotationCount = (int) DateUtil.getTimeDiffInSeconds(lastInsertTimestamp, currentTimestamp);
        // count means valid data excluding stale
        int count = transactionsStatistics.length - rotationCount;

        Statistic statistic = new Statistic();
        if (count <= transactionsStatistics.length && count > 0) {
            statistic = Arrays.stream(transactionsStatistics).limit(count).filter(e -> e != null).reduce(this::updateStatistic)
                    .orElse(statistic);
        }
        return statistic;
    }

    /**
     * This method is consolidation of two statistic.
     * 
     * @param statstic1 first statistic
     * @param statstic2 second statistic
     * @return combination of both the statistics.
     */
    private Statistic updateStatistic(Statistic statstic1, Statistic statstic2) {
        Statistic stats = new Statistic();
        stats.setSum(Double.sum(statstic1.getSum(), statstic2.getSum()));
        stats.setCount(Long.sum(statstic1.getCount(), statstic2.getCount()));
        stats.setAvg(stats.getSum() / stats.getCount());
        stats.setMax(Double.max(statstic1.getMax(), statstic2.getMax()));
        stats.setMin(Double.min(statstic1.getMin(), statstic2.getMin()));
        return stats;
    }

}
