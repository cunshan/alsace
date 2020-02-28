package com.alsace.framework.common.basic;

import com.github.pagehelper.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper基类.
 */
public interface BaseMapper<T extends BaseEntity> {

  /**
   * 根据条件获取列表. 带分页
   */
  Page<T> queryPage(@Param("entity") T domain);

  /**
   * 按照ID删除对象
   */
  void deleteByIds(@Param("entity") T domain,List<Long> ids);

  /**
   * 根据条件查询单条记录
   */
  T findOneByCondition(@Param("entity") T domain);

}
