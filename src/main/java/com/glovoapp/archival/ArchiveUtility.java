package com.glovoapp.archival;

import com.glovoapp.archival.read.InputConnector;
import com.glovoapp.archival.write.OutputConnector;
import io.vertx.core.json.JsonObject;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArchiveUtility {

    private final InputConnector inputConnector;
    private final OutputConnector outputConnector;

    public void archive() {
        Map<String, List<JsonObject>> data = inputConnector.read();
        outputConnector.write(data);
        inputConnector.deleteArchivedData();
    }

}
