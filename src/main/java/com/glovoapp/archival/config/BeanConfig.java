package com.glovoapp.archival.config;

import io.vertx.core.Vertx;
import io.vertx.jdbcclient.JDBCConnectOptions;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.PoolOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeanConfig {

  private final Vertx vertx;

  public JDBCPool jdbcPool() {
    return JDBCPool.pool(
        vertx,
        // configure the connection
        new JDBCConnectOptions()
            // H2 connection string
            .setJdbcUrl("jdbc:mysql://localhost:3306/courier-onboarding?useUnicode=true&characterEncoding=UTF-8")
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
}
