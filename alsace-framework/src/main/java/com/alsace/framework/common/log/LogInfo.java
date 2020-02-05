package com.alsace.framework.common.log;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogInfo implements Serializable{

  private static final long serialVersionUID = -2755519508064150511L;
  private String operateId;//切面操作ID
  private String className;//调用class类名称
  private String methodName;//调用方法名称
  private String argsJson;//参数JSON

  private String operationType;//操作类型
}
