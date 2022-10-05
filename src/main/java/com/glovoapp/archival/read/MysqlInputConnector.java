package com.glovoapp.archival.read;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.JsonObject;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.Row;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MysqlInputConnector implements InputConnector {

  private final JDBCPool pool;

  @SneakyThrows
  @Override
  public String read() {
    List<JsonObject> records = new ArrayList<>();
    return pool
        .query("SELECT * FROM referral_delivered_orders")
        .execute()
        .toCompletionStage()
        .thenAccept(rows -> {
              for (Row row : rows) {
                records.add(row.toJson());
              }
            })
        .thenApply(rowsList -> records.toString())
        .toCompletableFuture()
        .get(1, TimeUnit.HOURS);
  }
}
