package dk.sunepoulsen.tes.http;

import dk.sunepoulsen.tes.json.JsonMapper;

import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Optional;

public class HttpResponseVerificator {
    private HttpResponse<String> httpResponse;

    public HttpResponseVerificator(HttpResponse<String> httpResponse) {
        this.httpResponse = httpResponse;
    }

    public void responseCode(int expected) {
        assert httpResponse.statusCode() == expected;
    }

    public void contentType(String expected) {
        Optional<String> contentType = httpResponse.headers().firstValue("Content-Type");
        assert contentType.isPresent();
        assert contentType.get().equals(expected);
    }

    public void contentTypeIsJson() {
        contentType("application/json");
    }

    public void noBody() {
        assert httpResponse.headers().firstValue("Content-Type").isEmpty();
        assert httpResponse.body().isEmpty();
    }

    public void bodyIsJson() {
        assert bodyAsJson() != null;
    }

    public String bodyAsText() {
        return httpResponse.body();
    }

    public Map<String, Object> bodyAsJson() {
        return bodyAsJsonOfType(Map.class);
    }

    public <T> T bodyAsJsonOfType(Class<T> clazz) {
        return JsonMapper.decodeJson(httpResponse.body(), clazz);
    }
}
