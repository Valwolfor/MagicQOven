package com.magicqoven.service.impl;

import com.google.cloud.bigquery.*;
import com.magicqoven.service.inter.BigQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BigQueryServiceImpl implements BigQueryService {

    BigQuery bigQuery;

    @Autowired
    public BigQueryServiceImpl(BigQuery bigQuery) {
        this.bigQuery = bigQuery;
    }

    @Override
    public String executeQuery(String query) throws InterruptedException {
        try {
            query = "SELECT\n" +
                    "    refresh_date AS Day,\n" +
                    "    term AS Top_Term,\n" +
                    "    -- These search terms are in the top 25 in the US each day.\n" +
                    "    rank,\n" +
                    "    score,\n" +
                    "    dma_id,\n" +
                    "    dma_name,\n" +
//                    "    percent_gain,\n" +
                    "FROM `bigquery-public-data.google_trends.top_rising_terms`\n" +
                    "WHERE\n" +
                    "        rank = 1\n" +
                    "  -- Choose only the top term each day.\n" +
                    "  AND refresh_date >= DATE_SUB(CURRENT_DATE(), INTERVAL 2 WEEK)\n" +
                    "  LIMIT 10";// +
//                    "GROUP BY Day, Top_Term, rank\n" +
//                    "ORDER BY Day DESC"; // Tu consulta aquí
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

            TableResult result = bigQuery.query(queryConfig);

            // Procesar los resultados aquí
            StringBuilder output = new StringBuilder();
            for (com.google.cloud.bigquery.FieldValueList row : result.iterateAll()) {
                output.append(row.toString()).append("\n");
            }

            return "Query executed! Resultados: " + output.toString();
        } catch (BigQueryException e) {
            return "Error al ejecutar la consulta: " + e.getMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

