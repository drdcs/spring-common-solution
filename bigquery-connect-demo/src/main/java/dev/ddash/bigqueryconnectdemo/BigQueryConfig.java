package dev.ddash.bigqueryconnectdemo;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Log4j2
@PropertySource(value = "${secrets.file.path}")
public class BigQueryConfig {

    @Value("${big.query.keyfile}")
    private String credentialsPath;

    @Bean
    public BigQuery bigQuery() throws IOException {
        log.info("Loading BigQuery with cred path {}",credentialsPath);
        return BigQueryOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(credentialsPath)))
                .build()
                .getService();
    }
}
