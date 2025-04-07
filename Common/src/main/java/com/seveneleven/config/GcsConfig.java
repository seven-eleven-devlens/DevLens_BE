package com.seveneleven.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Configuration
public class GcsConfig {
    @Value("${gcp.credentials.json-base64}")
    private String base64Json;

    @Value("${gcp.project-id}")
    private String projectId;

    @Bean
    public Storage storage() throws IOException {
        byte[] decoded = Base64.getDecoder().decode(base64Json);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(decoded));

        return StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build()
                .getService();
    }
}
