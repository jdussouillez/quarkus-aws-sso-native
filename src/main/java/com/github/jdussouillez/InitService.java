package com.github.jdussouillez;

import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.Duration;
import org.jboss.logging.Logger;
import software.amazon.awssdk.services.sts.StsAsyncClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse;

@Singleton
public class InitService {

    private static final Logger LOG = Logger.getLogger(InitService.class);

    @Inject
    protected StsAsyncClient client;

    @Startup
    void init() {
        whoami()
            .invoke(user -> LOG.info("I am " + user))
            .onFailure()
            .invoke(ex -> LOG.fatal("Error when fetching caller identity", ex))
            .await()
            .atMost(Duration.ofSeconds(10));
    }

    private Uni<String> whoami() {
        return Uni.createFrom()
            .completionStage(client.getCallerIdentity())
            .map(GetCallerIdentityResponse::userId);
    }
}
