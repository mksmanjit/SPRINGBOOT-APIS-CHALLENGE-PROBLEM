package org.n26.challenge.business.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * This is the Utility class for Date.
 * 
 * @author manjit kumar
 *
 */
public final class DateUtil {

    /**
     * constant of milli second.
     */
    public static final int ONE_MILLIS = 1000;

    /**
     * This method is converting localDate Time into time in epoch in millis in UTC time zone.
     * 
     * @param localDateTime localtime given
     * @return time in epoch in millis in UTC time zone.
     */
    public static long converToTimeStamp(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.UTC) * ONE_MILLIS;
    }
    
    /**
     * @param currentTimestamp
     * @param transactionTimestamp
     * @return
     */
    public static long getTimeDiffInSeconds(long firstTimestamp, long secondTimestamp) {
        return (secondTimestamp - firstTimestamp) / DateUtil.ONE_MILLIS;
    }
}
