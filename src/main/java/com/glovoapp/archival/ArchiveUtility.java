package com.glovoapp.archival;

import com.glovoapp.archival.read.InputConnector;
import com.glovoapp.archival.write.OutputConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArchiveUtility {

    private final InputConnector inputConnector;
    private final OutputConnector outputConnector;

    public void archive() {
        String data = inputConnector.read();
        outputConnector.write(data);
    }

}
