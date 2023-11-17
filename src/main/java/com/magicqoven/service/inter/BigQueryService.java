package com.magicqoven.service.inter;

import com.google.cloud.bigquery.TableResult;

public interface BigQueryService {
    String executeQuery(String query) throws InterruptedException;
}