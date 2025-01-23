package umc.th.juinjang.controller.monitoring;

import static umc.th.juinjang.model.common.LoggerProvider.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class APILoggerPrinter {
  private static final Logger logger = getLogger(APILoggerPrinter.class);

  public APILoggerPrinter() {
  }

  public void print(APILoggerGenerator apiLoggerGenerator) {
    logger.info(apiLoggerGenerator.generateLog());
  }
}
