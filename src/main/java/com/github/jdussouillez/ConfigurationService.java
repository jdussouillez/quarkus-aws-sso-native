package com.github.jdussouillez;

import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.Duration;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import software.amazon.awssdk.core.BytesWrapper;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

@Singleton
public class ConfigurationService {

    private static final Logger LOG = Logger.getLogger(ConfigurationService.class);

    @ConfigProperty(name = "myapp.aws.s3.config.bucket")
    protected String bucket;

    @ConfigProperty(name = "myapp.aws.s3.config.key")
    protected String key;

    @Inject
    protected S3AsyncClient s3Client;

    @Startup
    void init() {
        fetchConfiguration()
            .invoke(conf -> LOG.info("Configuration loaded successfully"))
            .onFailure()
            .invoke(ex -> LOG.fatal("Configuration couldn't load", ex))
            .await()
            .atMost(Duration.ofSeconds(10));
    }

    private Uni<String> fetchConfiguration() {
        return Uni.createFrom()
            .completionStage(
                s3Client.getObject(
                    GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build(),
                    AsyncResponseTransformer.toBytes()
                )
            )
            .map(BytesWrapper::asUtf8String);
    }
}
