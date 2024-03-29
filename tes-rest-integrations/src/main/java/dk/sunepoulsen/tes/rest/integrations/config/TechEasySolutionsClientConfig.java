package dk.sunepoulsen.tes.rest.integrations.config;

import dk.sunepoulsen.tes.json.JsonMapper;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.time.Duration;

public interface TechEasySolutionsClientConfig {

    HttpClient.Version httpClientVersion();
    HttpClient.Redirect httpClientFollowRedirects();
    Duration httpClientConnectTimeout();
    Duration httpClientRequestTimeout();

    SSLContext sslContext();

    JsonMapper jsonMapper();
}
