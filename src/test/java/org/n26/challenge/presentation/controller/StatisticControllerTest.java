package org.n26.challenge.presentation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.n26.challenge.ChallengeServiceApplication;
import org.n26.challenge.business.dao.TransactionDAO;
import org.n26.challenge.business.util.DateUtil;
import org.n26.challenge.persistence.entity.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(ChallengeServiceApplication.class)
@RunWith(SpringRunner.class)
public class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionDAO transactionDAO;

    /**
     * Get this value from the application.yml file
     */
    @Value("${statisticService.windowInSec}")
    private int windowInSec;

    @Test
    public void getStatistics_noData() throws Exception {
        transactionDAO.updateLastInsertTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now().minusMinutes(5)));
        MvcResult content = this.mockMvc
                .perform(get("/statistics").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();

        Assert.assertEquals(content.getResponse().getContentAsString(),
                "{\"sum\":0.0,\"avg\":0.0,\"max\":0.0,\"min\":0.0,\"count\":0}");
    }

    @Test
    public void getStatistics_singleData() throws Exception {

        Statistic[] statisticsForLastSixtySec = new Statistic[windowInSec];
        Statistic statistic = new Statistic(5.0, 5.0, 5.0, 5.0, 1);
        statisticsForLastSixtySec[8] = statistic;
        transactionDAO.updateTransactionsStatistics(statisticsForLastSixtySec);
        transactionDAO.updateLastInsertTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now().minusSeconds(windowInSec - 40)));

        MvcResult content = this.mockMvc
                .perform(get("/statistics").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();

        Assert.assertEquals(content.getResponse().getContentAsString(),
                "{\"sum\":5.0,\"avg\":5.0,\"max\":5.0,\"min\":5.0,\"count\":1}");
    }
}
