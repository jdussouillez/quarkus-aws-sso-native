# quarkus-aws-sso-native

Project to reproduce an issue with AWS SDK SSO in native mode with Quarkus.

## How to run

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
FATAL [com.git.jdu.InitService] (main) Error when fetching caller identity: software.amazon.awssdk.core.exception.SdkClientException: Unable to load credentials from any of the providers in the chain AwsCredentialsProviderChain(credentialsProviders=[SystemPropertyCredentialsProvider(), EnvironmentVariableCredentialsProvider(), WebIdentityTokenCredentialsProvider(), ProfileCredentialsProvider(profileName=default, profileFile=ProfileFile(sections=[profiles, sso-session], profiles=[Profile(name=default, properties=[sso_session, output, sso_role_name, region, sso_account_id]), Profile(name=ReadOnly, properties=[sso_session, output, sso_role_name, region, sso_account_id]), Profile(name=Admin, properties=[sso_session, output, sso_role_name, region, sso_account_id])])), ContainerCredentialsProvider(), InstanceProfileCredentialsProvider()]) : [SystemPropertyCredentialsProvider(): Unable to load credentials from system settings. Access key must be specified either via environment variable (AWS_ACCESS_KEY_ID) or system property (aws.accessKeyId)., EnvironmentVariableCredentialsProvider(): Unable to load credentials from system settings. Access key must be specified either via environment variable (AWS_ACCESS_KEY_ID) or system property (aws.accessKeyId)., WebIdentityTokenCredentialsProvider(): Either the environment variable AWS_WEB_IDENTITY_TOKEN_FILE or the javaproperty aws.webIdentityTokenFile must be set., ProfileCredentialsProvider(profileName=default, profileFile=ProfileFile(sections=[profiles, sso-session], profiles=[Profile(name=default, properties=[sso_session, output, sso_role_name, region, sso_account_id]), Profile(name=ReadOnly, properties=[sso_session, output, sso_role_name, region, sso_account_id]), Profile(name=Admin, properties=[sso_session, output, sso_role_name, region, sso_account_id])])): To use Sso related properties in the 'default' profile, the 'sso' service module must be on the class path., ContainerCredentialsProvider(): Cannot fetch credentials from container - neither AWS_CONTAINER_CREDENTIALS_FULL_URI or AWS_CONTAINER_CREDENTIALS_RELATIVE_URI environment variables are set., InstanceProfileCredentialsProvider(): Failed to load credentials from IMDS.]
	at software.amazon.awssdk.core.exception.SdkClientException$BuilderImpl.build(SdkClientException.java:130)
	at software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain.resolveCredentials(AwsCredentialsProviderChain.java:130)
	at software.amazon.awssdk.auth.credentials.internal.LazyAwsCredentialsProvider.resolveCredentials(LazyAwsCredentialsProvider.java:45)
	at software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider.resolveCredentials(DefaultCredentialsProvider.java:129)
	at software.amazon.awssdk.auth.credentials.AwsCredentialsProvider.resolveIdentity(AwsCredentialsProvider.java:54)
	at software.amazon.awssdk.services.sts.auth.scheme.internal.StsAuthSchemeInterceptor.lambda$trySelectAuthScheme$4(StsAuthSchemeInterceptor.java:134)
	at software.amazon.awssdk.core.internal.util.MetricUtils.reportDuration(MetricUtils.java:80)
	at software.amazon.awssdk.services.sts.auth.scheme.internal.StsAuthSchemeInterceptor.trySelectAuthScheme(StsAuthSchemeInterceptor.java:134)
	at software.amazon.awssdk.services.sts.auth.scheme.internal.StsAuthSchemeInterceptor.selectAuthScheme(StsAuthSchemeInterceptor.java:81)
	at software.amazon.awssdk.services.sts.auth.scheme.internal.StsAuthSchemeInterceptor.beforeExecution(StsAuthSchemeInterceptor.java:61)
	at software.amazon.awssdk.core.interceptor.ExecutionInterceptorChain.lambda$beforeExecution$1(ExecutionInterceptorChain.java:59)
	at java.base@21.0.6/java.util.ArrayList.forEach(ArrayList.java:1596)
	at software.amazon.awssdk.core.interceptor.ExecutionInterceptorChain.beforeExecution(ExecutionInterceptorChain.java:59)
	at software.amazon.awssdk.awscore.internal.AwsExecutionContextBuilder.runInitialInterceptors(AwsExecutionContextBuilder.java:254)
	at software.amazon.awssdk.awscore.internal.AwsExecutionContextBuilder.invokeInterceptorsAndCreateExecutionContext(AwsExecutionContextBuilder.java:144)
	at software.amazon.awssdk.awscore.client.handler.AwsAsyncClientHandler.invokeInterceptorsAndCreateExecutionContext(AwsAsyncClientHandler.java:63)
	at software.amazon.awssdk.core.internal.handler.BaseAsyncClientHandler.lambda$execute$1(BaseAsyncClientHandler.java:75)
	at software.amazon.awssdk.core.internal.handler.BaseAsyncClientHandler.measureApiCallSuccess(BaseAsyncClientHandler.java:294)
	at software.amazon.awssdk.core.internal.handler.BaseAsyncClientHandler.execute(BaseAsyncClientHandler.java:73)
	at software.amazon.awssdk.awscore.client.handler.AwsAsyncClientHandler.execute(AwsAsyncClientHandler.java:49)
	at software.amazon.awssdk.services.sts.DefaultStsAsyncClient.getCallerIdentity(DefaultStsAsyncClient.java:1108)
	at software.amazon.awssdk.services.sts.StsAsyncClient.getCallerIdentity(StsAsyncClient.java:1753)
	at software.amazon.awssdk.services.sts.StsAsyncClient_eFoSmgrcO48ZCGUYLp5mka9QBUA_Synthetic_ClientProxy.getCallerIdentity(Unknown Source)
	at com.github.jdussouillez.InitService.whoami(InitService.java:32)
	at com.github.jdussouillez.InitService.init(InitService.java:22)
	at com.github.jdussouillez.InitService_Observer_Synthetic_L1DGp3b4fZULsIKPpdhyeWn8OVM.notify(Unknown Source)
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
                    Profile(name=Admin, properties=[sso_session, output, sso_role_name, region, sso_account_id])
                ]
            )
        ),
        ContainerCredentialsProvider(),
        InstanceProfileCredentialsProvider()
    ]
) : [
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
            ]
        )
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

Doc: https://docs.quarkiverse.io/quarkus-amazon-services/dev/amazon-sts.html#_going_asynchronous

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
             <artifactId>quarkus-amazon-sts</artifactId>
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
@@ -4,3 +4,6 @@ quarkus.sts.devservices.enabled=false

+# Use CRT instead of Netty
+quarkus.sts.async-client.type=aws-crt
```

</details>

### Add reflection metadata for AWS SSO service

See https://github.com/aws/aws-sdk-java-v2/issues/5840

Still failing in native mode.

<details>
<summary>Add reflection metadata for AWS SSO service</summary>

```diff
diff --git a/src/main/resources/META-INF/native-image/software.amazon.awssdk/sso/reflect-config.json b/src/main/resources/META-INF/native-image/software.amazon.awssdk/sso/reflect-config.json
new file mode 100644
index 0000000..7c1d1eb
--- /dev/null
+++ b/src/main/resources/META-INF/native-image/software.amazon.awssdk/sso/reflect-config.json
@@ -0,0 +1,11 @@
+[
+    {
+        "name": "software.amazon.awssdk.services.sso.auth.SsoProfileCredentialsProviderFactory",
+        "methods": [
+            {
+                "name": "<init>",
+                "parameterTypes": []
+            }
+        ]
+    }
+]
```

</details>

### Delay initialization of AWS SDK SSO classes

Still failing in native mode.

<details>
<summary>Delay initialization of AWS SDK SSO classes</summary>

```diff
diff --git a/pom.xml b/pom.xml
index 2aa1f27..054b0f0 100644
--- a/pom.xml
+++ b/pom.xml
@@ -28,6 +28,9 @@
             </activation>
             <properties>
                 <quarkus.native.enabled>true</quarkus.native.enabled>
+                <quarkus.native.additional-build-args>
+                    --initialize-at-run-time=software.amazon.awssdk.services.sso\,software.amazon.awssdk.services.sso.auth
+                </quarkus.native.additional-build-args>
             </properties>
         </profile>
     </profiles>
```

</details>

### Miscellaneous

I also tried to play with `quarkus.native.auto-service-loader-registration=true`, or `--initialize-at-run-time` to delay
the initialization of `ProfileCredentialsProvider`/`SsoProfileCredentialsProviderFactory`.

But for now, nothing seems to work, or even giving me other exception messages.
