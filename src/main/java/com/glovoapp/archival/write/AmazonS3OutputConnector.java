package com.glovoapp.archival.write;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.amazonaws.services.s3.model.CannedAccessControlList.BucketOwnerFullControl;

@Component
@RequiredArgsConstructor
public class AmazonS3OutputConnector implements OutputConnector {

    private final AmazonS3 s3Client;

    @Override
    public void write(String data) {
        PutObjectRequest request = new PutObjectRequest("courier-onboarding", "referral-orders-delivered", data).withCannedAcl(BucketOwnerFullControl);
        s3Client.putObject(request);
    }
}
