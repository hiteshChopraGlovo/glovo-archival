package com.glovoapp.archival.write;

import io.vertx.core.json.JsonObject;
import java.util.List;
import java.util.Map;

public interface OutputConnector {

    void write(Map<String, List<JsonObject>> data);

}
