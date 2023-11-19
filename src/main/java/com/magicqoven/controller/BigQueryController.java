package com.magicqoven.controller;

import com.magicqoven.entity.DTO.QueryParameters;
import com.magicqoven.service.impl.BigQueryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Provides REST endpoint allowing you to load data files to BigQuery using Spring Integration.
 */
@RestController
@RequestMapping("/bigquery")
public class BigQueryController {

    private final BigQueryServiceImpl service;

    @Autowired
    public BigQueryController(BigQueryServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/query")
    public String executeQuery() throws InterruptedException {
        String sqlQuery = "SELECT dma_id, dma_name, term, week, score, rank, refresh_date FROM  `bigquery-public-data.google_trends.top_terms` WHERE score IS NOT NULL LIMIT 10";
        return service.executeQuery(sqlQuery);
    }

    @PostMapping("/dynamic")
    public ResponseEntity<String> findTopTermsDynamic(@RequestBody QueryParameters parameters) {
        try {
            String result = service.findTopTermsDynamically(parameters);
            return ResponseEntity.ok(result);
        } catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error executing query: " + e.getMessage());
        }
    }
}