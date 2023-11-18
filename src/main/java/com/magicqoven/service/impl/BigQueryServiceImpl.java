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

        try {
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
            TableResult result = bigQuery.query(queryConfig);

            List<JsonObject> jsonList = new ArrayList<>();

            for (FieldValueList fieldValues : result.iterateAll()) {
                Integer fieldName = 1;
                JsonObject jsonObject = new JsonObject();
                for (FieldValue fieldValue : fieldValues) {

                    String value = fieldValue.getValue().toString();
                    jsonObject.addProperty(fieldName.toString(), value);
                    fieldName++;
                }
                jsonList.add(jsonObject);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(jsonList);

        } catch (BigQueryException e) {
            throw new BigQueryException(e.getErrors());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//                QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
//
//                TableResult result = bigQuery.query(queryConfig);
//
//                // Procesar los resultados aquí
//                StringBuilder output = new StringBuilder();
//                for (com.google.cloud.bigquery.FieldValueList row : result.iterateAll()) {
//                    output.append(row.toString()).append("\n");
//                }
//
//                List<FieldValue> firstRow = results.get(0); // Obtén la primera fila de resultados
//
//// Itera sobre los FieldValue para encontrar la clave 'refresh_date'
//                for (FieldValue fieldValue : firstRow) {
//                    if ("refresh_date".equals(fieldValue.getAttribute())) {
//                        System.out.println("Valor de refresh_date: " + fieldValue.getValue());
//                        break; // Se encontró el valor, salimos del bucle
//                    }
//
//                    return result.iterateAll().toString();
//                } catch(BigQueryException e){
//                    throw new BigQueryException(e.getErrors());
//                } catch(InterruptedException e){
//                    throw new RuntimeException(e);
//                }
    }

//    private String parseTableResultToJson(TableResult tableResult) {
//        List<JsonObject> jsonList = new ArrayList<>();
//
//        for (FieldValueList row : tableResult.iterateAll()) {
//            JsonObject jsonObject = new JsonObject();
//            for (FieldValueList.FieldValue fieldValue : row) {
//                String fieldName = fieldValue.getName();
//                String value = fieldValue.getValue().toString();
//                jsonObject.addProperty(fieldName, value);
//            }
//            jsonList.add(jsonObject);
//        }
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        return gson.toJson(jsonList);
//    }

}

