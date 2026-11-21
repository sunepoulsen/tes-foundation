package dk.sunepoulsen.tes.rest.integrations;

import dk.sunepoulsen.tes.json.JsonMapper;
import dk.sunepoulsen.tes.rest.integrations.config.DefaultClientConfig;
import dk.sunepoulsen.tes.rest.integrations.config.TechEasySolutionsClientConfig;
import dk.sunepoulsen.tes.rest.integrations.generators.DefaultTransactionIdsGenerator;
import dk.sunepoulsen.tes.rest.integrations.generators.TransactionIdsGenerator;
import dk.sunepoulsen.tes.springboot.backend.logging.RequestTransaction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Http Client to make calls to a Tech Enterprise Labs service.
 */
@Slf4j
public class TechEasySolutionsClient {

    static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    static final String BEARER_TOKEN_NAME = "Bearer";
    /** Log message for request URLs. */
    private static final String CALL_URL_LOG_MESSAGE = "Call {} {}";
    /** Log message for request headers. */
    private static final String HEADER_LOG_MESSAGE = "\t{}: {}";
    /** Placeholder for missing header values. */
    private static final String UNKNOWN_HEADER_LOG_PARAM = "Unknown";

    /** Base URI used to resolve request URLs. */
    @Getter
    private final URI uri;

    /** HTTP client configuration. */
    @Getter
    private final TechEasySolutionsClientConfig config;
    /** HTTP client used to send requests. */
    private final HttpClient client;

    /** Handler used to verify responses. */
    private final ResponseHandler responseHandler;
    /** Generator for request transaction identifiers. */
    private final TransactionIdsGenerator transactionIdsGenerator;

    /**
     * Creates an HTTP client.
     *
     * @param uri base URI for requests
     */
    public TechEasySolutionsClient(URI uri) {
        this(uri, new DefaultTransactionIdsGenerator());
    }

    /**
     * Creates an HTTP client.
     *
     * @param uri base URI for requests
     * @param transactionIdsGenerator generator for transaction identifiers
     */
    public TechEasySolutionsClient(URI uri, TransactionIdsGenerator transactionIdsGenerator) {
        this(uri, new DefaultClientConfig(), transactionIdsGenerator);
    }

    /**
     * Creates an HTTP client.
     *
     * @param uri base URI for requests
     * @param config client configuration
     */
    public TechEasySolutionsClient(URI uri, TechEasySolutionsClientConfig config) {
        this(uri, config, new DefaultTransactionIdsGenerator());
    }

    /**
     * Creates an HTTP client.
     *
     * @param uri base URI for requests
     * @param config client configuration
     * @param transactionIdsGenerator generator for transaction identifiers
     */
    public TechEasySolutionsClient(URI uri, TechEasySolutionsClientConfig config, TransactionIdsGenerator transactionIdsGenerator) {
        this.uri = uri;
        this.config = config;
        this.transactionIdsGenerator = transactionIdsGenerator;

        this.client = buildHttpClient();
        this.responseHandler = new ResponseHandler(config.jsonMapper());
    }

    /**
     * Sends an asynchronous GET request.
     *
     * @param <T> response type
     * @param url request URL resolved against the base URI
     * @param clazz response type
     * @return future completed when the request finishes with the decoded response
     */
    public <T> CompletableFuture<T> get(String url, Class<T> clazz) {
        return executeRequest("GET", url, Collections.emptyMap(), clazz);
    }

    /**
     * Sends an asynchronous GET request.
     *
     * @param <T> response type
     * @param url request URL resolved against the base URI
     * @param authorizationToken bearer token
     * @param clazz response type
     * @return future completed when the request finishes with the decoded response
     */
    public <T> CompletableFuture<T> get(String url, String authorizationToken, Class<T> clazz) {
        return executeRequest("GET", url, auhtorizationMap(authorizationToken), clazz);
    }

    /**
     * Sends an asynchronous POST request.
     *
     * @param <T> request body type
     * @param <R> response type
     * @param url request URL resolved against the base URI
     * @param bodyValue request body to encode as JSON
     * @param clazzResult response type
     * @return future completed when the request finishes with the decoded response
     */
    public <T, R> CompletableFuture<R> post(String url, T bodyValue, Class<R> clazzResult) {
        return executeRequest("POST", url, Collections.emptyMap(), bodyValue, clazzResult);
    }

    /**
     * Sends an asynchronous POST request.
     *
     * @param <T> request body type
     * @param <R> response type
     * @param url request URL resolved against the base URI
     * @param authorizationToken bearer token
     * @param bodyValue request body to encode as JSON
     * @param clazzResult response type
     * @return future completed when the request finishes with the decoded response
     */
    public <T, R> CompletableFuture<R> post(String url, String authorizationToken, T bodyValue, Class<R> clazzResult) {
        return executeRequest("POST", url, auhtorizationMap(authorizationToken), bodyValue, clazzResult);
    }

    /**
     * Sends an asynchronous PUT request.
     *
     * @param <T> request body type
     * @param <R> response type
     * @param url request URL resolved against the base URI
     * @param bodyValue request body to encode as JSON
     * @param clazzResult response type
     * @return future completed when the request finishes with the decoded response
     */
    public <T, R> CompletableFuture<R> put(String url, T bodyValue, Class<R> clazzResult) {
        return executeRequest("PUT", url, Collections.emptyMap(), bodyValue, clazzResult);
    }

    /**
     * Sends an asynchronous PUT request.
     *
     * @param <T> request body type
     * @param <R> response type
     * @param url request URL resolved against the base URI
     * @param authorizationToken bearer token
     * @param bodyValue request body to encode as JSON
     * @param clazzResult response type
     * @return future completed when the request finishes with the decoded response
     */
    public <T, R> CompletableFuture<R> put(String url, String authorizationToken, T bodyValue, Class<R> clazzResult) {
        return executeRequest("PUT", url, auhtorizationMap(authorizationToken), bodyValue, clazzResult);
    }

    /**
     * Sends an asynchronous PATCH request.
     *
     * @param <T> request body type
     * @param <R> response type
     * @param url request URL resolved against the base URI
     * @param bodyValue request body to encode as JSON
     * @param clazzResult response type
     * @return future completed when the request finishes with the decoded response
     */
    public <T, R> CompletableFuture<R> patch(String url, T bodyValue, Class<R> clazzResult) {
        return executeRequest("PATCH", url, Collections.emptyMap(), bodyValue, clazzResult);
    }

    /**
     * Sends an asynchronous PATCH request.
     *
     * @param <T> request body type
     * @param <R> response type
     * @param url request URL resolved against the base URI
     * @param authorizationToken bearer token
     * @param bodyValue request body to encode as JSON
     * @param clazzResult response type
     * @return future completed when the request finishes with the decoded response
     */
    public <T, R> CompletableFuture<R> patch(String url, String authorizationToken, T bodyValue, Class<R> clazzResult) {
        return executeRequest("PATCH", url, auhtorizationMap(authorizationToken), bodyValue, clazzResult);
    }

    /**
     * Sends an asynchronous DELETE request.
     *
     * @param url request URL resolved against the base URI
     * @return future completed when the request finishes
     */
    public CompletableFuture<Void> delete(String url) {
        return executeRequest("DELETE", url, Collections.emptyMap());
    }

    /**
     * Sends an asynchronous DELETE request.
     *
     * @param url request URL resolved against the base URI
     * @param authorizationToken bearer token
     * @return future completed when the request finishes
     */
    public CompletableFuture<Void> delete(String url, String authorizationToken) {
        return executeRequest("DELETE", url, auhtorizationMap(authorizationToken));
    }

    private CompletableFuture<Void> executeRequest(String method, String url, Map<String, String> extraHeaders) {
        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
            .method(method, HttpRequest.BodyPublishers.noBody())
            .uri(uri.resolve(url))
            .header(RequestTransaction.OPERATION_ID_HEADER_NAME, transactionIdsGenerator.operationId().generate().toString())
            .header(RequestTransaction.TRANSACTION_ID_HEADER_NAME, transactionIdsGenerator.transactionId().generate())
            .timeout(config.httpClientRequestTimeout());

        extraHeaders.forEach(httpRequestBuilder::header);

        HttpRequest httpRequest = httpRequestBuilder.build();
        log.debug(CALL_URL_LOG_MESSAGE, method, uri.resolve(url));
        log.debug(HEADER_LOG_MESSAGE, RequestTransaction.OPERATION_ID_HEADER_NAME, httpRequest.headers().firstValue(RequestTransaction.OPERATION_ID_HEADER_NAME).orElse(UNKNOWN_HEADER_LOG_PARAM));
        log.debug(HEADER_LOG_MESSAGE, RequestTransaction.TRANSACTION_ID_HEADER_NAME, httpRequest.headers().firstValue(RequestTransaction.TRANSACTION_ID_HEADER_NAME).orElse(UNKNOWN_HEADER_LOG_PARAM));

        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(responseHandler::verifyResponseAndExtractBody)
            .thenAccept(s -> {});
    }

    private <T> CompletableFuture<T> executeRequest(String method, String url, Map<String, String> extraHeaders, Class<T> clazzResult) {
        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
            .method(method, HttpRequest.BodyPublishers.noBody())
            .uri(uri.resolve(url))
            .header(RequestTransaction.OPERATION_ID_HEADER_NAME, transactionIdsGenerator.operationId().generate().toString())
            .header(RequestTransaction.TRANSACTION_ID_HEADER_NAME, transactionIdsGenerator.transactionId().generate())
            .timeout(config.httpClientRequestTimeout());

        extraHeaders.forEach(httpRequestBuilder::header);

        HttpRequest httpRequest = httpRequestBuilder.build();
        log.debug(CALL_URL_LOG_MESSAGE, method, uri.resolve(url));
        log.debug(HEADER_LOG_MESSAGE, RequestTransaction.OPERATION_ID_HEADER_NAME, httpRequest.headers().firstValue(RequestTransaction.OPERATION_ID_HEADER_NAME).orElse(UNKNOWN_HEADER_LOG_PARAM));
        log.debug(HEADER_LOG_MESSAGE, RequestTransaction.TRANSACTION_ID_HEADER_NAME, httpRequest.headers().firstValue(RequestTransaction.TRANSACTION_ID_HEADER_NAME).orElse(UNKNOWN_HEADER_LOG_PARAM));

        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(responseHandler::verifyResponseAndExtractBody)
            .thenApply(s -> JsonMapper.decodeJson(s, clazzResult));
    }

    private <T, R> CompletableFuture<R> executeRequest(String method, String url, Map<String, String> extraHeaders, T bodyValue, Class<R> clazzResult) {
        String requestBody = config.jsonMapper().encode(bodyValue);
        log.trace("Request body: {}", requestBody);

        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
            .method(method, HttpRequest.BodyPublishers.ofString(requestBody))
            .uri(uri.resolve(url))
            .header("Content-Type", "application/json")
            .header(RequestTransaction.OPERATION_ID_HEADER_NAME, transactionIdsGenerator.operationId().generate().toString())
            .header(RequestTransaction.TRANSACTION_ID_HEADER_NAME, transactionIdsGenerator.transactionId().generate())
            .timeout(config.httpClientRequestTimeout());

        extraHeaders.forEach(httpRequestBuilder::header);

        HttpRequest httpRequest = httpRequestBuilder.build();
        log.debug(CALL_URL_LOG_MESSAGE, method, uri.resolve(url));
        log.debug(HEADER_LOG_MESSAGE, RequestTransaction.OPERATION_ID_HEADER_NAME, httpRequest.headers().firstValue(RequestTransaction.OPERATION_ID_HEADER_NAME).orElse(UNKNOWN_HEADER_LOG_PARAM));
        log.debug(HEADER_LOG_MESSAGE, RequestTransaction.TRANSACTION_ID_HEADER_NAME, httpRequest.headers().firstValue(RequestTransaction.TRANSACTION_ID_HEADER_NAME).orElse(UNKNOWN_HEADER_LOG_PARAM));

        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(responseHandler::verifyResponseAndExtractBody)
            .thenApply(s -> JsonMapper.decodeJson(s, clazzResult));
    }

    private Map<String, String> auhtorizationMap(String authorizationToken) {
        return Collections.singletonMap(AUTHORIZATION_HEADER_NAME, BEARER_TOKEN_NAME + " " + authorizationToken);
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
