package dk.sunepoulsen.tes.rest.integrations;

import dk.sunepoulsen.tes.json.JsonMapper;
import dk.sunepoulsen.tes.rest.integrations.config.DefaultClientConfig;
import dk.sunepoulsen.tes.rest.integrations.config.TechEasySolutionsClientConfig;
import dk.sunepoulsen.tes.rest.integrations.generators.TransactionIdsGenerator;
import dk.sunepoulsen.tes.rest.integrations.generators.DefaultTransactionIdsGenerator;
import dk.sunepoulsen.tes.rest.models.NoContent;
import dk.sunepoulsen.tes.springboot.backend.logging.RequestTransaction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * Http Client to make calls to a Tech Enterprise Labs service.
 */
@Slf4j
public class TechEasySolutionsClient {

    private static final String CALL_URL_LOG_MESSAGE = "Call {} {}";

    @Getter
    private final URI uri;

    @Getter
    private final TechEasySolutionsClientConfig config;
    private final HttpClient client;

    private final ResponseHandler responseHandler;
    private final TransactionIdsGenerator transactionIdsGenerator;

    public TechEasySolutionsClient(URI uri) {
        this(uri, new DefaultTransactionIdsGenerator());
    }

    public TechEasySolutionsClient(URI uri, TransactionIdsGenerator transactionIdsGenerator) {
        this(uri, new DefaultClientConfig(), transactionIdsGenerator);
    }

    public TechEasySolutionsClient(URI uri, TechEasySolutionsClientConfig config) {
        this(uri, config, new DefaultTransactionIdsGenerator());
    }

    public TechEasySolutionsClient(URI uri, TechEasySolutionsClientConfig config, TransactionIdsGenerator transactionIdsGenerator) {
        this.uri = uri;
        this.config = config;
        this.transactionIdsGenerator = transactionIdsGenerator;

        this.client = buildHttpClient();
        this.responseHandler = new ResponseHandler(config.jsonMapper());
    }

    public <T> CompletableFuture<T> get(String url, Class<T> clazz) {
        return executeRequest("GET", url, clazz);
    }

    public <T, R> CompletableFuture<R> post(String url, T bodyValue, Class<R> clazzResult) {
        return executeRequest("POST", url, bodyValue, clazzResult);
    }

    public <T, R> CompletableFuture<R> put(String url, T bodyValue, Class<R> clazzResult) {
        return executeRequest("PUT", url, bodyValue, clazzResult);
    }

    public <T, R> CompletableFuture<R> patch(String url, T bodyValue, Class<R> clazzResult) {
        return executeRequest("PATCH", url, bodyValue, clazzResult);
    }

    public CompletableFuture<NoContent> delete(String url) {
        return executeRequest("DELETE", url);
    }

    private CompletableFuture<NoContent> executeRequest(String method, String url) {
        log.debug(CALL_URL_LOG_MESSAGE, method, uri.resolve(url));
        HttpRequest httpRequest = HttpRequest.newBuilder()
            .method(method, HttpRequest.BodyPublishers.noBody())
            .uri(uri.resolve(url))
            .header(RequestTransaction.OPERATION_ID_HEADER_NAME, transactionIdsGenerator.operationId().generate().toString())
            .header(RequestTransaction.TRANSACTION_ID_HEADER_NAME, transactionIdsGenerator.transactionId().generate())
            .timeout(config.httpClientRequestTimeout())
            .build();

        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(responseHandler::verifyResponseAndExtractBody)
            .thenApply(s -> new NoContent());
    }

    private <T> CompletableFuture<T> executeRequest(String method, String url, Class<T> clazzResult) {
        log.debug(CALL_URL_LOG_MESSAGE, method, uri.resolve(url));
        HttpRequest httpRequest = HttpRequest.newBuilder()
            .method(method, HttpRequest.BodyPublishers.noBody())
            .uri(uri.resolve(url))
            .header(RequestTransaction.OPERATION_ID_HEADER_NAME, transactionIdsGenerator.operationId().generate().toString())
            .header(RequestTransaction.TRANSACTION_ID_HEADER_NAME, transactionIdsGenerator.transactionId().generate())
            .timeout(config.httpClientRequestTimeout())
            .build();

        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(responseHandler::verifyResponseAndExtractBody)
            .thenApply(s -> JsonMapper.decodeJson(s, clazzResult));
    }

    private <T, R> CompletableFuture<R> executeRequest(String method, String url, T bodyValue, Class<R> clazzResult) {
        log.debug(CALL_URL_LOG_MESSAGE, method, uri.resolve(url));

        String requestBody = config.jsonMapper().encode(bodyValue);
        log.trace("Request body: {}", requestBody);

        HttpRequest httpRequest = HttpRequest.newBuilder()
            .method(method, HttpRequest.BodyPublishers.ofString(requestBody))
            .uri(uri.resolve(url))
            .header("Content-Type", "application/json")
            .header(RequestTransaction.OPERATION_ID_HEADER_NAME, transactionIdsGenerator.operationId().generate().toString())
            .header(RequestTransaction.TRANSACTION_ID_HEADER_NAME, transactionIdsGenerator.transactionId().generate())
            .timeout(config.httpClientRequestTimeout())
            .build();

        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(responseHandler::verifyResponseAndExtractBody)
            .thenApply(s -> JsonMapper.decodeJson(s, clazzResult));
    }

    private HttpClient buildHttpClient() {
        HttpClient.Builder builder = HttpClient.newBuilder()
            .version(config.httpClientVersion())
            .followRedirects(config.httpClientFollowRedirects())
            .connectTimeout(config.httpClientConnectTimeout());

        if (config.sslContext() != null) {
            builder.sslContext(config.sslContext());
        }

        return builder.build();
    }
}
