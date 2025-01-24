package umc.th.juinjang.controller.monitoring;

import static umc.th.juinjang.utils.LoggerProvider.getLogger;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import org.slf4j.Logger;

public class CustomContentCachingHttpRequestWrapper extends HttpServletRequestWrapper {

  private final Logger logger = getLogger(CustomContentCachingHttpRequestWrapper.class);
  private final byte[] cachedBody;

  public CustomContentCachingHttpRequestWrapper(HttpServletRequest request) {
    super(request);
    byte[] tempBody = new byte[0];
    try {
      tempBody = request.getInputStream().readAllBytes();
    } catch (Exception e) {
      logger.error("CustomContentCachingHttpRequestWrapper init error {}", e.getMessage());
    } finally {
      this.cachedBody = tempBody;
    }
  }

  @Override
  public ServletInputStream getInputStream() {
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

  public byte[] getCachedBody() {
    return this.cachedBody;
  }
}
