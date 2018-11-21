package org.n26.challenge.business.service;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.n26.challenge.business.dao.TransactionDAO;
import org.n26.challenge.business.util.DateUtil;
import org.n26.challenge.exception.TransactionExpiredException;
import org.n26.challenge.persistence.entity.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StatisticServiceTest {

    @Autowired
    private StatisticService statisticService;

    /**
     * Get this value from the application.yml file
     */
    @Value("${statisticService.windowInSec}")
    private int windowInSec;

    @Autowired
    private TransactionDAO transactionDAO;

    @Test
    public void processOk_stateData() throws TransactionExpiredException {
        Statistic[] statisticsForLastSixtySec = new Statistic[windowInSec];
        Statistic statistic = new Statistic(5.0, 5.0, 5.0, 5.0, 1);
        statisticsForLastSixtySec[8] = statistic;
        transactionDAO.updateTransactionsStatistics(statisticsForLastSixtySec);
        transactionDAO
                .updateLastInsertTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now().minusSeconds(windowInSec)));
        Statistic fetchedStatistic = this.statisticService.getStatistics();
        Assert.assertEquals(fetchedStatistic, new Statistic());
    }

    @Test
    public void getStatistics_singleDataPresent() throws Exception {

        Statistic[] statisticsForLastSixtySec = new Statistic[windowInSec];
        Statistic statistic = new Statistic(5.0, 5.0, 5.0, 5.0, 1);
        statisticsForLastSixtySec[0] = statistic;
        transactionDAO.updateTransactionsStatistics(statisticsForLastSixtySec);
        transactionDAO.updateLastInsertTimestamp(
                DateUtil.converToTimeStamp(LocalDateTime.now().minusSeconds(windowInSec / 30)));

        Statistic fetchedStatistic = statisticService.getStatistics();
        Assert.assertEquals(statistic, fetchedStatistic);
    }

    @Test
    public void getStatistics_fullDataPresent() throws Exception {

        Statistic[] statisticsForLastSixtySec = createData();
        transactionDAO.updateTransactionsStatistics(statisticsForLastSixtySec);
        transactionDAO.updateLastInsertTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now()));

        Statistic fetchedStatistic = statisticService.getStatistics();
        Assert.assertEquals(new Statistic(5.0 * windowInSec, 5.0, windowInSec - 1, 0.0, windowInSec), fetchedStatistic);
    }

    @Test
    public void getStatistics_fullDataPresentButHalfStale() throws Exception {

        Statistic[] statisticsForLastSixtySec = createData();
        transactionDAO.updateTransactionsStatistics(statisticsForLastSixtySec);
        transactionDAO.updateLastInsertTimestamp(
                DateUtil.converToTimeStamp(LocalDateTime.now().minusSeconds(windowInSec / 2)));

        Statistic fetchedStatistic = statisticService.getStatistics();
        Assert.assertEquals(new Statistic(5.0 * windowInSec / 2, 5.0, windowInSec / 2 - 1, 0.0, windowInSec / 2),
                fetchedStatistic);
    }

    @Test
    public void getStatistics_fullDataPresentButOneIsNotExpired() throws Exception {

        Statistic[] statisticsForLastSixtySec = createData();
        transactionDAO.updateTransactionsStatistics(statisticsForLastSixtySec);
        transactionDAO.updateLastInsertTimestamp(
                DateUtil.converToTimeStamp(LocalDateTime.now().minusSeconds(windowInSec - 1)));

        Statistic fetchedStatistic = statisticService.getStatistics();
        Assert.assertEquals(new Statistic(5.0, 5.0, 0.0, 0.0, 1), fetchedStatistic);
    }

    @Test
    public void getStatistics_fullDataPresentButOneAllStale() throws Exception {

        Statistic[] statisticsForLastSixtySec = createData();
        transactionDAO.updateTransactionsStatistics(statisticsForLastSixtySec);
        transactionDAO
                .updateLastInsertTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now().minusSeconds(windowInSec)));

        Statistic fetchedStatistic = statisticService.getStatistics();
        Assert.assertEquals(new Statistic(), fetchedStatistic);
    }
    
    private Statistic[] createData() {
        Statistic[] statisticsForLastSixtySec = new Statistic[windowInSec];
        for (int i = 0; i < windowInSec; i++) {
            statisticsForLastSixtySec[i] = new Statistic(5.0, 5.0, i, i, 1);
        }
        return statisticsForLastSixtySec;
    }

}
