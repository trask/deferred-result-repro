## Build

```
./gradlew assemble
```

## Run

```
java -javaagent:opentelemetry-javaagent-2.15.0.jar -Dotel.metrics.exporter=none -Dotel.logs.exporter=none -Dotel.traces.exporter=console -Dotel.instrumentation.common.experimental.controller-telemetry.enabled=true -jar build/libs/deferred-result-repro.jar
```

## Hit the url

```
curl http://localhost:8080/deferred
```

Output

> INFO io.opentelemetry.exporter.logging.LoggingSpanExporter - 'TestController.deferred' : 3befc96f7efd803741ec82e758248085 8980bc96765162f8 INTERNAL [tracer: io.opentelemetry.spring-webmvc-6.0:2.15.0-alpha] AttributesMap{data={thread.id=37, thread.name=http-nio-8080-exec-1, code.namespace=com.github.trask.repro.TestController, code.function=deferred}, capacity=128, totalAddedValues=4}
>
> INFO io.opentelemetry.exporter.logging.LoggingSpanExporter - 'TestController.deferred' : 3befc96f7efd803741ec82e758248085 471e903aaba1c6ee INTERNAL [tracer: io.opentelemetry.spring-webmvc-6.0:2.15.0-alpha] AttributesMap{data={thread.id=37, thread.name=http-nio-8080-exec-1, code.namespace=com.github.trask.repro.TestController, code.function=deferred}, capacity=128, totalAddedValues=4}

When running with 2.14.0, this is the output:

> INFO io.opentelemetry.exporter.logging.LoggingSpanExporter - 'TestController.deferred' : 87e82bcfe099aee2d72ffc206e9d7b07 92a7fd9d1dd22c23 INTERNAL [tracer: io.opentelemetry.spring-webmvc-6.0:2.14.0-alpha] AttributesMap{data={code.function=deferred, code.namespace=com.github.trask.repro.TestController, thread.name=http-nio-8080-exec-1, thread.id=37}, capacity=128, totalAddedValues=4}
>
> INFO io.opentelemetry.exporter.logging.LoggingSpanExporter - 'TestController.deferred' : 87e82bcfe099aee2d72ffc206e9d7b07 6a1487cb78f31860 INTERNAL [tracer: io.opentelemetry.spring-webmvc-6.0:2.14.0-alpha] AttributesMap{data={code.function=deferred, code.namespace=com.github.trask.repro.TestController, thread.name=http-nio-8080-exec-1, thread.id=37}, capacity=128, totalAddedValues=4}
>
> INFO io.opentelemetry.exporter.logging.LoggingSpanExporter - 'GET /deferred' : 87e82bcfe099aee2d72ffc206e9d7b07 c326b80bd7aa3e10 SERVER [tracer: io.opentelemetry.tomcat-10.0:2.14.0-alpha] AttributesMap{data={http.request.method=GET, http.route=/deferred, server.address=localhost, client.address=0:0:0:0:0:0:0:1, network.peer.address=0:0:0:0:0:0:0:1, url.path=/deferred, network.peer.port=53700, network.protocol.version=1.1, user_agent.original=curl/8.12.1, url.scheme=http, thread.name=http-nio-8080-exec-1, thread.id=37, http.response.status_code=200, server.port=8080}, capacity=128, totalAddedValues=14}

(which is still not ideal due to the duplicated INTERNAL spans, but at least the SERVER span is captured)
