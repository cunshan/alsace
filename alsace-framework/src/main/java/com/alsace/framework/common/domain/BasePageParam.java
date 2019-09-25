package com.alsace.framework.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * mapper查询参数实体基类
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class BasePageParam extends BaseEntity {

  private static final long serialVersionUID = 5229290423424178461L;

  /**
   * 分页查询使用
   */
  private Integer pageNum;
  private Integer pageSize;

}
