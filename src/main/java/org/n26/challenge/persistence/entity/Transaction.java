package org.n26.challenge.persistence.entity;

/**
 * POJO for holding transaction detail.
 * 
 * @author manjit kumar
 *
 */
public class Transaction {

    /**
     * Holds the transaction amount.
     */
    private double amount;
    /**
     * Holds transaction time in epoch in millis in UTC time zone (this is not current timestamp)
     */
    private long timestamp;

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
