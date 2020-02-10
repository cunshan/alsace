package com.alsace.framework.common.shiro;

import java.io.Serializable;
import lombok.Data;

@Data
public class ShiroPrincipal implements Serializable {

  private static final long serialVersionUID = 5341445603278745824L;

  private String loginAccount;//登录账号
  private String password;//密码
  private String displayName;//用户名称

}
