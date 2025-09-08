package dk.sunepoulsen.tes.deployment.core.data;

import lombok.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class DeployFileContent {
    private final String filename;
    private final byte[] content;

    public InputStream getContentAsInputStream() {
        return new ByteArrayInputStream(content);
    }

    public String getContentAsString() {
        return getContentAsString(StandardCharsets.UTF_8);
    }

    public String getContentAsString(Charset  charset) {
        return new String(content, charset);
    }
}
