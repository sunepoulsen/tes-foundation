package dk.sunepoulsen.tes.rest.integrations.config;

import dk.sunepoulsen.tes.json.JsonMapper;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.time.Duration;

/** Configuration used when constructing and sending HTTP requests. */
public interface TechEasySolutionsClientConfig {

    /** @return HTTP protocol version */
    HttpClient.Version httpClientVersion();
    /** @return redirect policy */
    HttpClient.Redirect httpClientFollowRedirects();
    /** @return connection timeout */
    Duration httpClientConnectTimeout();
    /** @return request timeout */
    Duration httpClientRequestTimeout();

    /** @return SSL context, or {@code null} to use the client default */
    SSLContext sslContext();

    /** @return JSON mapper used for request and response handling */
    JsonMapper jsonMapper();
}
