package com.magicqoven;/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



// [START bigquery_simple_app_all]
// [START bigquery_simple_app_deps]

import com.google.api.client.auth.oauth2.Credential;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// [END bigquery_simple_app_deps]

public class SimpleApp {

    static String  pathToCredentials = "src/main/resources/magicqoven-key_service.json";
    private static String projectId = "magicqoven";
    public static void main(String... args) throws Exception {
        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(new FileInputStream(pathToCredentials));



        // [START bigquery_simple_app_client]
        BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId(projectId).setCredentials(credentials).build().getService();
        // [END bigquery_simple_app_client]
        // [START bigquery_simple_app_query]
        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(
                                "SELECT\n" +
                                        "    refresh_date AS Day,\n" +
                                        "    term AS Top_Term,\n" +
                                        "    -- These search terms are in the top 25 in the US each day.\n" +
                                        "    rank,\n" +
                                        "FROM `bigquery-public-data.google_trends.top_terms`\n" +
                                        "WHERE\n" +
                                        "        rank = 1\n" +
                                        "  -- Choose only the top term each day.\n" +
                                        "  AND refresh_date >= DATE_SUB(CURRENT_DATE(), INTERVAL 2 WEEK)\n" +
                                        "  -- Filter to the last 2 weeks.\n" +
                                        "GROUP BY Day, Top_Term, rank\n" +
                                        "ORDER BY Day DESC")
                        // Use standard SQL syntax for queries.
                        // See: https://cloud.google.com/bigquery/sql-reference/
                        .setUseLegacySql(false)
                        .build();

        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        // Wait for the query to complete.
        queryJob = queryJob.waitFor();

        // Check for errors
        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            // You can also look at queryJob.getStatus().getExecutionErrors() for all
            // errors, not just the latest one.
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }
        // [END bigquery_simple_app_query]

        // [START bigquery_simple_app_print]
        // Get the results.
        TableResult result = queryJob.getQueryResults();

        // Print all pages of the results.
        for (FieldValueList row : result.iterateAll()) {
            // String type
            String day = row.get("Day").getStringValue();
            String viewCount = row.get("Top_Term").getStringValue();
            String rank = row.get("rank").getStringValue();
            System.out.printf("%s Day: %s Views, %s Rank\n", day, viewCount, rank);
        }
        // [END bigquery_simple_app_print]
    }
}
// [END bigquery_simple_app_all]