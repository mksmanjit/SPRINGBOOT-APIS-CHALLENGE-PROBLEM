package org.n26.challenge.presentation.json;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * POJO for holding JSON value of transaction.
 * 
 * @author manjit kumar
 *
 */
public class TransactionPostJson {

    /**
     * transaction amount.
     */
    @NotNull
    @Min(0)
    private Double amount;

    /**
     * transaction time in epoch in millis in UTC time zone (this is not current timestamp).
     */
    @NotNull
    @Min(0)
    private Long timestamp;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
