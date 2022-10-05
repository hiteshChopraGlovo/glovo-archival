package com.glovoapp.archival.read;

import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.Row;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MysqlInputConnector implements InputConnector {

  private final JDBCPool pool;

  @Override
  public List<List<Object>> read() {
    List<List<Object>> records = new ArrayList<>();
    pool
        .query("SELECT * FROM referral_delivered_orders")
        .execute()
        .onFailure(e -> {
          // handle the failure
        })
        .onSuccess(rows -> {
          for (Row row : rows) {
            System.out.println(row);
          }
        });
    return records;
  }
}
