package com.glovoapp.archival.write;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.StringUtils;
import io.vertx.core.json.JsonObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.amazonaws.services.s3.model.CannedAccessControlList.BucketOwnerFullControl;

@Component
@RequiredArgsConstructor
public class AmazonS3OutputConnector implements OutputConnector {

    private final AmazonS3 s3Client;

    @Override
    public void write(Map<String, List<JsonObject>> data) {
        data.forEach((date, dataRow) -> {
            byte[] contentBytes = dataRow.toString().getBytes(StringUtils.UTF8);
            InputStream is = new ByteArrayInputStream(contentBytes);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("text/plain");
            metadata.setContentLength(contentBytes.length);
            PutObjectRequest request = new PutObjectRequest("courier-onboarding", "referral-delivered-orders-"+date, is, metadata).withCannedAcl(BucketOwnerFullControl);
            s3Client.putObject(request);
        });
    }
}
