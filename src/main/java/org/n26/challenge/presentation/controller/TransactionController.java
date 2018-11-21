package org.n26.challenge.presentation.controller;

import javax.validation.Valid;

import org.n26.challenge.business.service.TransactionService;
import org.n26.challenge.exception.TransactionExpiredException;
import org.n26.challenge.persistence.entity.Transaction;
import org.n26.challenge.presentation.json.TransactionPostJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is accepting the request from the User for adding transaction.
 * This REST service contain:
 * POST /transactions
 * 
 * @author manjit kumar
 *
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * This is the POST End point for adding transaction.
     * 
     * @param bodyJson holds the JSON data of transaction.
     * @return 201 if successfully added and 204 if transaction time is older than given interval.
     */
    @PostMapping
    public ResponseEntity<Transaction> post(@RequestBody @Valid TransactionPostJson bodyJson) {

        try {
            Transaction transaction = new Transaction();
            transaction.setAmount(bodyJson.getAmount());
            transaction.setTimestamp(bodyJson.getTimestamp());

            transactionService.add(transaction);

            ResponseEntity<Transaction> response = new ResponseEntity<>(HttpStatus.CREATED);
            return response;
        } catch (TransactionExpiredException e) {
            return new ResponseEntity<Transaction>(HttpStatus.NO_CONTENT);
        }
    }

}
