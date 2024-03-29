package dk.sunepoulsen.tes.rest.integrations.config;

import dk.sunepoulsen.tes.json.JsonMapper;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.time.Duration;

public class DefaultClientConfig implements TechEasySolutionsClientConfig {
    private final JsonMapper jsonMapper;
    private SSLContext sslContext;

    public DefaultClientConfig() {
        this(null);
    }

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
