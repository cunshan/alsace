package com.alsace.framework.common.log;

import com.alsace.framework.utils.GsonUtils;
import com.alsace.framework.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@Async
public class SimpleLogService implements LogService {

  @Override
  public void saveLog(LogInfo logInfo) {

    LogUtils.printInfo(log, "持久化保存日志信息:{}", GsonUtils.toJson(logInfo));

  }
}
