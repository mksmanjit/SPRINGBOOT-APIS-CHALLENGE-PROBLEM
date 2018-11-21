package org.n26.challenge.business.util;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.n26.challenge.business.util.DateUtil;

public class DateUtilTest {

    @Test
    public void converToTimeStamp_success() {
        Long timestamp = DateUtil.converToTimeStamp(LocalDateTime.of(2017, 10, 01, 9, 45, 30));
        Assert.assertEquals(Long.valueOf(1506851130000l), timestamp);
    }
    
    @Test
    public void getTimeDiffInSeconds_success() {
        long timestamp = DateUtil.getTimeDiffInSeconds(6000, 9000);
        Assert.assertEquals(3, timestamp);
    }

}
