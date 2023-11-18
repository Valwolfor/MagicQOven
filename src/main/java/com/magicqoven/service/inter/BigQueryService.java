package com.magicqoven.service.inter;

import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.TableResult;
import com.google.gson.Gson;
import com.magicqoven.entity.DTO.QueryParameters;

public interface BigQueryService {
    String executeQuery(String query) throws InterruptedException, BigQueryException;
    public String findTopTermsDynamically(QueryParameters parameters) throws InterruptedException;
}