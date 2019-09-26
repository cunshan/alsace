package com.alsace.service.user.domain;

import com.alsace.framework.common.basic.BasePageParam;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "sys_user")
public class User extends BasePageParam {

  private static final long serialVersionUID = 2752628782288751122L;

  private String userName;
  private String password;

}
