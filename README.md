# quarkus-aws-sso-native

Project to reproduce an issue with AWS SDK SSO in native mode with Quarkus.

## How to run

First, customize your S3 connection (region, bucket, key) there in the [config file](./src/main/resources/application.properties).

```sh
# Quarkus dev mode - Pass
./mvnw quarkus:dev

# JAR mode - Pass
./mvnw package && java -jar target/quarkus-app/quarkus-run.jar

# Native mode - Fail
./mvnw package -Dnative && ./target/quarkus-aws-sso-native-0.0.0-SNAPSHOT-runner
```

Stacktrace in native mode:

```
FATAL [com.git.jdu.ConfigurationService] (main) Configuration couldn't load: software.amazon.awssdk.core.exception.SdkClientException: Unable to load credentials from any of the providers in the chain AwsCredentialsProviderChain(credentialsProviders=[SystemPropertyCredentialsProvider(), EnvironmentVariableCredentialsProvider(), WebIdentityTokenCredentialsProvider(), ProfileCredentialsProvider(profileName=default, profileFile=ProfileFile(sections=[profiles, sso-session], profiles=[Profile(name=default, properties=[sso_session, output, sso_role_name, region, sso_account_id]), Profile(name=ReadOnly, properties=[sso_session, output, sso_role_name, region, sso_account_id]), Profile(name=Admin, properties=[sso_session, output, sso_role_name, region, sso_account_id])])), ContainerCredentialsProvider(), InstanceProfileCredentialsProvider()]) : [SystemPropertyCredentialsProvider(): Unable to load credentials from system settings. Access key must be specified either via environment variable (AWS_ACCESS_KEY_ID) or system property (aws.accessKeyId)., EnvironmentVariableCredentialsProvider(): Unable to load credentials from system settings. Access key must be specified either via environment variable (AWS_ACCESS_KEY_ID) or system property (aws.accessKeyId)., WebIdentityTokenCredentialsProvider(): Either the environment variable AWS_WEB_IDENTITY_TOKEN_FILE or the javaproperty aws.webIdentityTokenFile must be set., ProfileCredentialsProvider(profileName=default, profileFile=ProfileFile(sections=[profiles, sso-session], profiles=[Profile(name=default, properties=[sso_session, output, sso_role_name, region, sso_account_id]), Profile(name=ReadOnly, properties=[sso_session, output, sso_role_name, region, sso_account_id]), Profile(name=Admin, properties=[sso_session, output, sso_role_name, region, sso_account_id])])): To use Sso related properties in the 'default' profile, the 'sso' service module must be on the class path., ContainerCredentialsProvider(): Cannot fetch credentials from container - neither AWS_CONTAINER_CREDENTIALS_FULL_URI or AWS_CONTAINER_CREDENTIALS_RELATIVE_URI environment variables are set., InstanceProfileCredentialsProvider(): Failed to load credentials from IMDS.]
	at software.amazon.awssdk.core.exception.SdkClientException$BuilderImpl.build(SdkClientException.java:130)
	at software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain.resolveCredentials(AwsCredentialsProviderChain.java:130)
	at software.amazon.awssdk.auth.credentials.internal.LazyAwsCredentialsProvider.resolveCredentials(LazyAwsCredentialsProvider.java:45)
	at software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider.resolveCredentials(DefaultCredentialsProvider.java:129)
	at software.amazon.awssdk.auth.credentials.AwsCredentialsProvider.resolveIdentity(AwsCredentialsProvider.java:54)
	at software.amazon.awssdk.services.s3.auth.scheme.internal.S3AuthSchemeInterceptor.lambda$trySelectAuthScheme$6(S3AuthSchemeInterceptor.java:169)
	at software.amazon.awssdk.core.internal.util.MetricUtils.reportDuration(MetricUtils.java:80)
	at software.amazon.awssdk.services.s3.auth.scheme.internal.S3AuthSchemeInterceptor.trySelectAuthScheme(S3AuthSchemeInterceptor.java:169)
	at software.amazon.awssdk.services.s3.auth.scheme.internal.S3AuthSchemeInterceptor.selectAuthScheme(S3AuthSchemeInterceptor.java:87)
	at software.amazon.awssdk.services.s3.auth.scheme.internal.S3AuthSchemeInterceptor.beforeExecution(S3AuthSchemeInterceptor.java:67)
	at software.amazon.awssdk.core.interceptor.ExecutionInterceptorChain.lambda$beforeExecution$1(ExecutionInterceptorChain.java:59)
	at java.base@21.0.6/java.util.ArrayList.forEach(ArrayList.java:1596)
	at software.amazon.awssdk.core.interceptor.ExecutionInterceptorChain.beforeExecution(ExecutionInterceptorChain.java:59)
	at software.amazon.awssdk.awscore.internal.AwsExecutionContextBuilder.runInitialInterceptors(AwsExecutionContextBuilder.java:254)
	at software.amazon.awssdk.awscore.internal.AwsExecutionContextBuilder.invokeInterceptorsAndCreateExecutionContext(AwsExecutionContextBuilder.java:144)
	at software.amazon.awssdk.awscore.client.handler.AwsAsyncClientHandler.invokeInterceptorsAndCreateExecutionContext(AwsAsyncClientHandler.java:63)
	at software.amazon.awssdk.core.internal.handler.BaseAsyncClientHandler.lambda$execute$3(BaseAsyncClientHandler.java:116)
	at software.amazon.awssdk.core.internal.handler.BaseAsyncClientHandler.measureApiCallSuccess(BaseAsyncClientHandler.java:294)
	at software.amazon.awssdk.core.internal.handler.BaseAsyncClientHandler.execute(BaseAsyncClientHandler.java:89)
	at software.amazon.awssdk.awscore.client.handler.AwsAsyncClientHandler.execute(AwsAsyncClientHandler.java:56)
	at software.amazon.awssdk.services.s3.DefaultS3AsyncClient.getObject(DefaultS3AsyncClient.java:6448)
	at software.amazon.awssdk.services.s3.S3AsyncClient_-oB8th8X-ncY703rR4eB7-XuHB8_Synthetic_ClientProxy.getObject(Unknown Source)
	at com.github.jdussouillez.ConfigurationService.fetchConfiguration(ConfigurationService.java:42)
	at com.github.jdussouillez.ConfigurationService.init(ConfigurationService.java:31)
	at com.github.jdussouillez.ConfigurationService_Observer_Synthetic_h5PXZ5YqlQjISvFkVInIA2GyB4U.notify(Unknown Source)
	at io.quarkus.arc.impl.EventImpl$Notifier.notifyObservers(EventImpl.java:365)
	at io.quarkus.arc.impl.EventImpl$Notifier.notify(EventImpl.java:347)
	at io.quarkus.arc.impl.EventImpl.fire(EventImpl.java:81)
	at io.quarkus.arc.runtime.ArcRecorder.fireLifecycleEvent(ArcRecorder.java:163)
	at io.quarkus.arc.runtime.ArcRecorder.handleLifecycleEvents(ArcRecorder.java:114)
	at io.quarkus.runner.recorded.LifecycleEventsBuildStep$startupEvent1144526294.deploy_0(Unknown Source)
	at io.quarkus.runner.recorded.LifecycleEventsBuildStep$startupEvent1144526294.deploy(Unknown Source)
	at io.quarkus.runner.ApplicationImpl.doStart(Unknown Source)
	at io.quarkus.runtime.Application.start(Application.java:101)
	at io.quarkus.runtime.ApplicationLifecycleManager.run(ApplicationLifecycleManager.java:119)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:80)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:51)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:144)
	at io.quarkus.runner.GeneratedMain.main(Unknown Source)
```

Error message (formatted):

```
Unable to load credentials from any of the providers in the chain AwsCredentialsProviderChain(  
    credentialsProviders=[
        SystemPropertyCredentialsProvider(),  
        EnvironmentVariableCredentialsProvider(),
        WebIdentityTokenCredentialsProvider(),
        ProfileCredentialsProvider(
            profileName=default,
            profileFile=ProfileFile(
                sections=[profiles, sso-session],
                profiles=[
                    Profile(name=default, properties=[sso_session, output, sso_role_name, region, sso_account_id]),
                    Profile(name=ReadOnly, properties=[sso_session, output, sso_role_name, region, sso_account_id]),
                    Profile(name=Admin, properties=[sso_session, output, sso_role_name, region, sso_account_id])])),
                    ContainerCredentialsProvider(), InstanceProfileCredentialsProvider()
    ]) : [
        SystemPropertyCredentialsProvider(): Unable to load credentials from system settings. Access key must be specified either via environment variable (AWS_ACCESS_KEY_ID) or system property (aws.accessKeyId).,
        EnvironmentVariableCredentialsProvider(): Unable to load credentials from system settings. Access key must be specified either via environment variable (AWS_ACCESS_KEY_ID) or system property (aws.accessKeyId).,
        WebIdentityTokenCredentialsProvider(): Either the environment variable AWS_WEB_IDENTITY_TOKEN_FILE or the javaproperty aws.webIdentityTokenFile must be set.,
        ProfileCredentialsProvider(
            profileName=default,
            profileFile=ProfileFile(
                sections=[profiles, sso-session],
                profiles=[
                    Profile(name=default, properties=[sso_session, output, sso_role_name, region, sso_account_id]),
                    Profile(name=ReadOnly, properties=[sso_session, output, sso_role_name, region, sso_account_id]),
                    Profile(name=Admin, properties=[sso_session, output, sso_role_name, region, sso_account_id])
                ])
        ): To use Sso related properties in the 'default' profile, the 'sso' service module must be on the class path.,
        ContainerCredentialsProvider(): Cannot fetch credentials from container - neither AWS_CONTAINER_CREDENTIALS_FULL_URI or AWS_CONTAINER_CREDENTIALS_RELATIVE_URI environment variables are set.,
        InstanceProfileCredentialsProvider(): Failed to load credentials from IMDS.
    ]
```

## Related links

- https://stackoverflow.com/questions/79179322/quarkus-is-not-finding-some-aws-classes-when-trying-to-use-neptune
- https://stackoverflow.com/questions/71913703/quarkus-native-image-build-fails-using-aws-sdk
- https://github.com/aws/aws-sdk-java-v2/issues/5840

## Tentatives to fix it

### CRT-based HTTP client instead of Netty

Doc: https://docs.quarkiverse.io/quarkus-amazon-services/dev/amazon-s3.html#_going_asynchronous

Still failing in native mode.

<details>
<summary>CRT-based HTTP client instead of Netty</summary>

```diff
diff --git a/pom.xml b/pom.xml
index 2aa1f27..0e510ca 100644
--- a/pom.xml
+++ b/pom.xml
@@ -63,6 +63,10 @@
             <groupId>io.quarkiverse.amazonservices</groupId>
             <artifactId>quarkus-amazon-s3</artifactId>
         </dependency>
+        <dependency>
+            <groupId>software.amazon.awssdk</groupId>
+            <artifactId>aws-crt-client</artifactId>
+        </dependency>
 
         <!-- AWS SSO -->
         <dependency>
diff --git a/src/main/resources/application.properties b/src/main/resources/application.properties
index a37d3c8..86b11fe 100644
--- a/src/main/resources/application.properties
+++ b/src/main/resources/application.properties
@@ -4,3 +4,6 @@ quarkus.s3.devservices.enabled=false
 
 myapp.aws.s3.config.bucket=hifi-filter-hifi-stack
 myapp.aws.s3.config.key=local/junior/hopi/config.json
+
+# Use CRT instead of Netty
+quarkus.s3.async-client.type=aws-crt
```

</details>

### CRT-based S3 client instead of Netty

Doc: https://docs.quarkiverse.io/quarkus-amazon-services/dev/amazon-s3.html#_going_asynchronous

Still failing in native mode.

<details>
<summary>CRT-based S3 client instead of Netty</summary>

```diff
diff --git a/pom.xml b/pom.xml
index 2aa1f27..0e510ca 100644
--- a/pom.xml
+++ b/pom.xml
@@ -63,6 +63,10 @@
             <groupId>io.quarkiverse.amazonservices</groupId>
             <artifactId>quarkus-amazon-s3</artifactId>
         </dependency>
+        <dependency>
+            <groupId>software.amazon.awssdk</groupId>
+            <artifactId>aws-crt-client</artifactId>
+        </dependency>
 
         <!-- AWS SSO -->
         <dependency>
diff --git a/src/main/java/com/github/jdussouillez/ConfigurationService.java b/src/main/java/com/github/jdussouillez/ConfigurationService.java
index 725bfc7..56f9999 100644
--- a/src/main/java/com/github/jdussouillez/ConfigurationService.java
+++ b/src/main/java/com/github/jdussouillez/ConfigurationService.java
@@ -1,5 +1,6 @@
 package com.github.jdussouillez;
 
+import io.quarkiverse.amazon.s3.runtime.S3Crt;
 import io.quarkus.runtime.Startup;
 import io.smallrye.mutiny.Uni;
 import jakarta.inject.Inject;
@@ -24,6 +25,7 @@ public class ConfigurationService {
     protected String key;
 
     @Inject
+    @S3Crt
     protected S3AsyncClient s3Client;
 
     @Startup
diff --git a/src/main/resources/application.properties b/src/main/resources/application.properties
index a37d3c8..86b11fe 100644
--- a/src/main/resources/application.properties
+++ b/src/main/resources/application.properties
@@ -4,3 +4,6 @@ quarkus.s3.devservices.enabled=false
 
 myapp.aws.s3.config.bucket=hifi-filter-hifi-stack
 myapp.aws.s3.config.key=local/junior/hopi/config.json
+
+# Use CRT instead of Netty
+quarkus.s3.async-client.type=aws-crt
```

</details>
