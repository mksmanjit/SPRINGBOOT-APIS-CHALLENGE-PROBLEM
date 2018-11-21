package org.n26.challenge.presentation.controller;

import org.n26.challenge.business.service.StatisticService;
import org.n26.challenge.persistence.entity.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is accepting the request from the User for fetching the statistics.
 * This REST service contain:
 * GET /statistics
 * 
 * @author manjit kumar
 *
 */
@RestController
@RequestMapping("/statistics")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    /**
     * This is the GET End point for fetching statistics in given interval.
     * 
     * @return 200 status code with JSON body containing statistics detail.
     */
    @GetMapping
    public ResponseEntity<Statistic> getStatistics() {

        Statistic statistic = statisticService.getStatistics();

        ResponseEntity<Statistic> response = new ResponseEntity<Statistic>(statistic, HttpStatus.OK);

        return response;
    }

}
