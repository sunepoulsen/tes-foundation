package dk.sunepoulsen.tes.http;

import dk.sunepoulsen.tes.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.shaded.com.google.common.net.MediaType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class HttpHelper {

    static final String POST = "POST";
    static final String GET = "GET";
    static final String PUT = "PUT";
    static final String PATCH = "PATCH";
    static final String DELETE = "DELETE";
    static final String HEAD = "HEAD";
    static final String OPTIONS = "OPTIONS";


    protected HttpClient httpClient;

    public HttpHelper() {
        initHttpClient();
    }

    public void initHttpClient() {
        initHttpClient(Duration.ofSeconds(30L));
    }

    public void initHttpClient(Duration timeout) {
        this.httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(timeout)
            .build();
    }

    public static HttpRequest.Builder newRequestBuilder(String url) throws URISyntaxException {
        return HttpRequest.newBuilder()
            .uri(new URI(url))
            .header("X-Request-ID", UUID.randomUUID().toString())
            .timeout(Duration.ofSeconds(30L));
    }

    public HttpRequest.Builder newRequestBuilder(GenericContainer container, String url) {
        return HttpRequest.newBuilder()
            .uri(URI.create(String.format("http://%s:%s", container.getHost(), container.getMappedPort(8080))).resolve(url))
            .header("X-Request-ID", UUID.randomUUID().toString())
            .timeout(Duration.ofSeconds(30L));
    }

    public HttpResponseVerificator sendRequest(HttpRequest httpRequest) throws IOException, InterruptedException {
        Optional<String> requestId = httpRequest.headers().firstValue("X-Request-ID");
        if( requestId.isEmpty() ) {
            log.info("Sending request ${httpRequest.method()} ${httpRequest.uri().toString()} with no X-Request-ID");
        }
        else {
            log.info("Sending request ${httpRequest.method()} ${httpRequest.uri().toString()} with X-Request-ID: ${requestId.get()}");
        }

        return new HttpResponseVerificator(httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()));
    }

    public HttpResponseVerificator sendValidRequest(GenericContainer container, String method, String url) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = newRequestBuilder(container, url)
            .method(method, HttpRequest.BodyPublishers.noBody());

        return sendRequest(requestBuilder.build());
    }

    public HttpResponseVerificator sendValidRequest(GenericContainer container, String method, String url, String contentType, HttpRequest.BodyPublisher bodyPublisher) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = newRequestBuilder(container, url)
            .method(method, bodyPublisher)
            .header("Content-Type", contentType);

        return sendRequest(requestBuilder.build());
    }

    public HttpResponseVerificator sendValidRequest(GenericContainer container, String method, String url, Object body) throws IOException, InterruptedException {
        return sendValidRequest(container,
            method,
            url,
            MediaType.JSON_UTF_8.toString(),
            HttpRequest.BodyPublishers.ofString(JsonMapper.encodeAsJson(body))
        );
    }
}
