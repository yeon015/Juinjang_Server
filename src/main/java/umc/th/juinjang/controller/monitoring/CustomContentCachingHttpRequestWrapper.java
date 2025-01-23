package umc.th.juinjang.controller.monitoring;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class CustomContentCachingHttpRequestWrapper extends HttpServletRequestWrapper {

  private final byte[] cachedBody;

  public CustomContentCachingHttpRequestWrapper(HttpServletRequest request) throws IOException {
    super(request);
    this.cachedBody = request.getInputStream().readAllBytes();
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cachedBody);
    return new ServletInputStream() {
      @Override
      public int read() {
        return byteArrayInputStream.read();
      }

      @Override
      public boolean isFinished() {
        return byteArrayInputStream.available() <= 0;
      }

      @Override
      public boolean isReady() {
        return true;
      }

      @Override
      public void setReadListener(ReadListener readListener) {
      }
    };
  }

  public String getRequestBody() {
    return new String(cachedBody, StandardCharsets.UTF_8).replaceAll("[\\n\\r\\t]", "").replaceAll("\\\\", "");
  }
}
