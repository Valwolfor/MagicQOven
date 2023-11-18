package com.magicqoven.service.inter;

import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.TableResult;
import com.google.gson.Gson;

public interface BigQueryService {
    String executeQuery(String query) throws InterruptedException, BigQueryException;
}