package umc.th.juinjang.model.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerProvider { // 커스텀 로거 생성 메서드
  public static Logger getLogger(Class<?> clazz) {
    return LoggerFactory.getLogger(clazz);
  }
}
