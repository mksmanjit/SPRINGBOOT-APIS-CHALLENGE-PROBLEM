package org.n26.challenge.presentation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.n26.challenge.ChallengeServiceApplication;
import org.n26.challenge.business.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(ChallengeServiceApplication.class)
@RunWith(SpringRunner.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Get this value from the application.yml file
     */
    @Value("${statisticService.windowInSec}")
    private int windowInSec;

    @Test
    public void postTransaction_success() throws Exception {

        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"amount\": 5.0");
        json.append(",");
        json.append("\"timestamp\": " + String.valueOf(DateUtil.converToTimeStamp(LocalDateTime.now())));
        json.append("}");

        MvcResult content = this.mockMvc
                .perform(post("/transactions").contentType("application/json;charset=UTF-8").content(json.toString())
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isCreated()).andReturn();

        Assert.assertEquals(content.getResponse().getContentAsString().length(), 0);
    }

    @Test
    public void postTransaction_failure() throws Exception {

        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"amount\": 5.0");
        json.append(",");
        json.append(
                "\"timestamp\": " + String.valueOf(DateUtil.converToTimeStamp(LocalDateTime.now().minusMinutes(2))));
        json.append("}");

        MvcResult content = this.mockMvc
                .perform(post("/transactions").contentType("application/json;charset=UTF-8").content(json.toString())
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isNoContent()).andReturn();

        Assert.assertEquals(content.getResponse().getContentAsString().length(), 0);
    }

}
