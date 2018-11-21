package org.n26.challenge.exception;

/**
 * This is checked exception used to thrown when transaction time is older then given interval.
 * 
 * @author manjit kumar.
 *
 */
public class TransactionExpiredException extends Exception {

    /**
     * serial versionUID for this class help in during serialization/de-serialization.
     */
    private static final long serialVersionUID = 1L;

}
