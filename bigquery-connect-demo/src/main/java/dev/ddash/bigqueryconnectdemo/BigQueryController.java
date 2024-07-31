package dev.ddash.bigqueryconnectdemo;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.bigquery.TableResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BigQueryController {

    private final BigQueryService bigQueryService;
    private final ObjectMapper objectMapper;

    public BigQueryController(BigQueryService bigQueryService, ObjectMapper objectMapper) {
        this.bigQueryService = bigQueryService;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping("/bigquery")
    public List<Map> getBiqQueryData(@RequestBody BigQueryQ query) throws InterruptedException {
        TableResult tableResult = bigQueryService.executeQuery(query.getQuery());
        List<Map> data = new ArrayList<>();
        tableResult.iterateAll().forEach(row -> {
            System.out.println(row);
            Map map = null;
            try {
                map = objectMapper.readValue(row.get(0).getValue().toString(), Map.class);
                data.add(map);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            System.out.println(map);
        });
        return data;
    }
}
