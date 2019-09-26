package com.alsace.framework.common.basic;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = 5025633853325720916L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Date createdTime;//创建时间
  private String createdBy;//创建人
  private Date modifiedTime;//修改时间
  private String modifiedBy;//修改人

}