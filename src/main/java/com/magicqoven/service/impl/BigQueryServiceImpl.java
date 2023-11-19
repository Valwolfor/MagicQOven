package com.magicqoven.service.impl;

import com.google.cloud.bigquery.*;
import com.google.cloud.spring.bigquery.core.BigQueryTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.magicqoven.entity.DTO.QueryParameters;
import com.magicqoven.service.inter.BigQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BigQueryServiceImpl implements BigQueryService {


    private final BigQueryTemplate bigQueryTemplate;
    private final BigQuery bigQuery;
    private final String datasetName;

    @Autowired
    public BigQueryServiceImpl(
                               BigQueryTemplate bigQueryTemplate, BigQuery bigQuery, @Value("${spring.cloud.gcp.credentials.location}") String name) {
        this.bigQueryTemplate = bigQueryTemplate;
        this.datasetName = name;
        this.bigQuery = bigQuery;
    }

    @Override
    public String executeQuery(String query) throws InterruptedException, BigQueryException {

        TableResult result;
        try {
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
            result = bigQuery.query(queryConfig);

            return parseTableResultToJson(result);
        } catch (BigQueryException e) {
            throw new BigQueryException(e.getErrors());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findTopTermsDynamically(QueryParameters parameters)
            throws InterruptedException {

        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(buildQuery(parameters)).build();

        TableResult result = bigQuery.query(queryConfig);

        return parseTableResultToJson(result);
    }

    private String buildQuery(QueryParameters parameters) {
        StringBuilder queryBuilder = new StringBuilder();
        String query;

        if (!parameters.getGroupedFields().isEmpty()) {
            query = selectBuiderGrouped(parameters, queryBuilder)
                    .append(" FROM `bigquery-public-data.google_trends.top_terms`")
                    .append(whereFiltersBuilder(parameters))
                    .append(groupByBuilder(parameters))
                    .append(limitBuilder(parameters)).toString();
        } else {
            queryBuilder.append("SELECT ");
            query = queryBuilder.append(String.join(", ", parameters.getSelectedFields()))
                    .append(" FROM `bigquery-public-data.google_trends.top_terms`")
                    .append(whereFiltersBuilder(parameters))
                    .append(orderByBuilder(parameters))
                    .append(limitBuilder(parameters)).toString();
        }
        return query;
    }

    private StringBuilder selectBuiderGrouped(QueryParameters parameters, StringBuilder queryBuilder) {
        queryBuilder.append("SELECT ");
        List<String> groupedFields = parameters.getGroupedFields();
        List<String> selectedFields = parameters.getSelectedFields();

        List<String> fieldsWithAnyValue = new ArrayList<>();
        selectedFields.forEach(field -> {
            if (!groupedFields.contains(field)) {
                fieldsWithAnyValue.add("ANY_VALUE(" + field + ") AS " + field);
            } else {
                fieldsWithAnyValue.add(field);
            }
        });

        return queryBuilder.append(String.join(", ", fieldsWithAnyValue));
    }

    private String whereFiltersBuilder(QueryParameters parameters) {
        StringBuilder queryBuilder = new StringBuilder();

        if (!parameters.getFilters().isEmpty()) {
            queryBuilder.append(" WHERE ");

            List<String> filterConditions = new ArrayList<>();
            parameters.getFilters().forEach((key, filterUnit) -> {
                String condition;

                if (filterUnit.getIsInt().equals("true")) {
                    condition = filterUnit.getField() + " " +
                            filterUnit.getOperator() + " " +
                            Integer.parseInt(filterUnit.getOperant());
                } else {
                    condition = filterUnit.getField() + " " +
                            filterUnit.getOperator() + " '" +
                            filterUnit.getOperant() + "'";
                }

                filterConditions.add(condition);
            });

            if (!filterConditions.isEmpty()) {

                if (!parameters.getSelectedOperators().isEmpty()) {
                    StringBuilder groupedConditions = new StringBuilder();

                    for (String condition : filterConditions) {
                        groupedConditions.append(condition);

                        if (!parameters.getSelectedOperators().isEmpty()) {
                            groupedConditions.append(" ").append(parameters.getSelectedOperators().poll()).append(" ");
                        }
                    }
                    queryBuilder.append(" ").append(groupedConditions);
                } else {
                    queryBuilder.append(" ").append(String.join(" AND ", filterConditions));
                }
            }
        }
        System.out.println(queryBuilder.toString());
        return queryBuilder.toString();
    }

    private String groupByBuilder(QueryParameters parameters) {
        StringBuilder queryBuilder = new StringBuilder();
        if (!parameters.getGroupedFields().isEmpty()) {
            queryBuilder.append(" GROUP BY ")
                    .append(String.join(", ", parameters.getGroupedFields()));
        }
        return queryBuilder.toString();
    }

    private String orderByBuilder(QueryParameters parameters) {
        StringBuilder queryBuilder = new StringBuilder();

        if (parameters.getSortField() != null) {
            queryBuilder.append(" ORDER BY ")
                    .append(parameters.getSortField()).append(" ").append(parameters.getSortDirection());
        }

        return queryBuilder.toString();
    }

    private String limitBuilder(QueryParameters parameters) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(" LIMIT ").append(parameters.getLimit());
        String query = queryBuilder.toString();

        return query;
    }

    private String parseTableResultToJson(TableResult result) {
        FieldList fields = result.getSchema().getFields();

        List<JsonObject> jsonList = new ArrayList<>();

        for (FieldValueList fieldValues : result.iterateAll()) {

            JsonObject jsonObject = new JsonObject();
            for (int i = 0; i < fieldValues.size(); i++) {
                Field fieldName = fields.get(i);
                FieldValue fieldValue = fieldValues.get(i);
                String value = fieldValue.getValue().toString();
                jsonObject.addProperty(fieldName.getName(), value);
            }
            jsonList.add(jsonObject);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonList);
    }
}


