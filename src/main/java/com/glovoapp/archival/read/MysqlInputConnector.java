package com.glovoapp.archival.read;
import io.vertx.core.json.JsonObject;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.Row;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  public Map<String, List<JsonObject>> read() {
    Map<String, List<JsonObject>> recordsMap = new HashMap<>();
    return pool
        .query("SELECT *,DATE_FORMAT(creation_time, '%Y-%m-%d') AS TEMP_DATE FROM referral_delivered_orders where creation_time < DATE_SUB(now(), INTERVAL 6 MONTH)")
        .execute()
        .toCompletionStage()
        .thenAccept(rows -> {
              for (Row row : rows) {
                JsonObject rowJsonObject = row.toJson();
                String date = rowJsonObject.getString("TEMP_DATE");
                rowJsonObject.remove("TEMP_DATE");
                if(!recordsMap.containsKey(date)) {
                  recordsMap.put(date, new ArrayList<>());
                }
                recordsMap.get(date).add(rowJsonObject);
              }
            })
        .thenApply(map -> recordsMap)
        .toCompletableFuture()
        .get(1, TimeUnit.HOURS);
  }

  @SneakyThrows
  @Override
  public void deleteArchivedData() {
     pool
        .query("DELETE from referral_delivered_orders where creation_time < DATE_SUB(now(), INTERVAL 6 MONTH)")
        .execute()
        .toCompletionStage()
        .toCompletableFuture()
        .get(1, TimeUnit.HOURS);
  }
}
