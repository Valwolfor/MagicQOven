package com.magicqoven.service.impl;

import com.google.cloud.bigquery.*;
import com.google.cloud.spring.bigquery.core.BigQueryTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.magicqoven.config.BigQueryFileGateway;
import com.magicqoven.service.inter.BigQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BigQueryServiceImpl implements BigQueryService {


    private final BigQueryFileGateway bigQueryFileGateway;
    private final BigQueryTemplate bigQueryTemplate;
    private final BigQuery bigQuery;
    private final String datasetName;

    @Autowired
    public BigQueryServiceImpl(BigQueryFileGateway bigQueryFileGateway,
                               BigQueryTemplate bigQueryTemplate, BigQuery bigQuery, @Value("${spring.cloud.gcp.credentials.location}") String name) {
        this.bigQueryFileGateway = bigQueryFileGateway;
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


