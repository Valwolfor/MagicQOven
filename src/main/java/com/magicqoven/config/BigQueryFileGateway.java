package com.magicqoven.config;

import com.google.cloud.bigquery.Job;
import com.google.cloud.spring.bigquery.integration.BigQuerySpringMessageHeaders;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

import java.util.concurrent.CompletableFuture;


/** Spring Integration gateway which allows sending data to load to BigQuery through a channel. */
@MessagingGateway
public interface BigQueryFileGateway {
    CompletableFuture<Job> writeToBigQueryTable(
            byte[] csvData, @Header(BigQuerySpringMessageHeaders.TABLE_NAME) String tableName);
}