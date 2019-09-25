package com.alsace.framework.common.domain;

import java.io.Serializable;
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

}
