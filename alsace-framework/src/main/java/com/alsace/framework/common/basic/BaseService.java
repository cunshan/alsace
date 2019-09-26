package com.alsace.framework.common.basic;

import java.util.List;

/**
 * service基类
 */
public interface BaseService<T,ID> {

  /**
   * 创建
   */
  T create(T domain);
  /**
   * 创建
   */
  List<T> createBatch(List<T> list);

  /**
   * 创建
   */
  T update(T domain);
  /**
   * 创建
   */
  List<T> updateBatch(List<T> list);


  /**
   * 根据ID获取实体类
   */
  T getById(ID id);

  /**
   * 根据ID删除数据
   */
  void deleteById(ID id);

  /**
   * 分页查询
   */
  List<T> queryPage(T param);

}
