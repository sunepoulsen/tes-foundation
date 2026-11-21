package dk.sunepoulsen.tes.rest.integrations.config;

import dk.sunepoulsen.tes.json.JsonMapper;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.time.Duration;

/**
 * Default HTTP client configuration with optional SSL context.
 */
public class DefaultClientConfig implements TechEasySolutionsClientConfig {
    /** JSON mapper used for request and response handling. */
    private final JsonMapper jsonMapper;
    /** Optional SSL context for the HTTP client. */
    private SSLContext sslContext;

    /** Creates a configuration without a custom SSL context. */
    public DefaultClientConfig() {
        this(null);
    }

    /**
     * Creates a configuration with an optional SSL context.
     *
     * @param sslContext SSL context, or {@code null} for the client default
     */
    public DefaultClientConfig(SSLContext sslContext) {
        this.jsonMapper = new JsonMapper();
        this.sslContext = sslContext;
    }

    @Override
    public HttpClient.Version httpClientVersion() {
        return HttpClient.Version.HTTP_1_1;
    }

    @Override
    public HttpClient.Redirect httpClientFollowRedirects() {
        return HttpClient.Redirect.NORMAL;
    }

    @Override
    public Duration httpClientConnectTimeout() {
        return Duration.ofSeconds(30);
    }

    @Override
    public Duration httpClientRequestTimeout() {
        return Duration.ofSeconds(30);
    }

    @Override
    public SSLContext sslContext() {
        return this.sslContext;
    }

    @Override
    public JsonMapper jsonMapper() {
        return this.jsonMapper;
    }
}
