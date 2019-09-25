package com.alsace.service.demo.mapper;

import com.alsace.service.demo.domain.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  /**
   * 分页查询
   */
  Page<User> queryPage(User param);
}
