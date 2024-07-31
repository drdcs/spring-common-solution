package dev.ddash.bigqueryconnectdemo;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import org.springframework.stereotype.Service;

@Service
public class BigQueryService {

    private final BigQuery bigQuery;

    public BigQueryService(BigQuery bigQuery) {
        this.bigQuery = bigQuery;
    }

    public TableResult executeQuery(String query) throws InterruptedException {
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
        return bigQuery.query(queryConfig);
    }
}
