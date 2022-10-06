package com.glovoapp.archival.read;

import io.vertx.core.json.JsonObject;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;

public interface InputConnector {

  Map<String, List<JsonObject>> read();

  void deleteArchivedData();
}
