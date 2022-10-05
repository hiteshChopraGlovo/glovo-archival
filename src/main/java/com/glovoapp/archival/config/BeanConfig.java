package com.glovoapp.archival.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.vertx.core.Vertx;
import io.vertx.jdbcclient.JDBCConnectOptions;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.PoolOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeanConfig {
  @Bean
  public Vertx vertx() {
    return Vertx.vertx();
  }

  @Bean
  public JDBCPool jdbcPool(@Autowired Vertx vertx) {
    return JDBCPool.pool(
        vertx,
        // configure the connection
        new JDBCConnectOptions()
            // JDBC connection string
            .setJdbcUrl("jdbc:mysql://localhost:3506/courier-onboarding?useUnicode=true&characterEncoding=UTF-8")
            // username
            .setUser("courier-onboarding-user")
            // password
            .setPassword("courier-onboarding-password"),
        // configure the pool
        new PoolOptions()
            .setMaxSize(16)
            .setName("pool-name")
    );
  }

  @Bean
  public AmazonS3 s3() {
    return AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new EndpointConfiguration("http://localhost:4577/", "eu-west-1"))
        .withPathStyleAccessEnabled(true)
        .build();
  }
}
