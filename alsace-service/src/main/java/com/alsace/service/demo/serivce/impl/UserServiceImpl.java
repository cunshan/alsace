package com.alsace.service.demo.serivce.impl;

import com.alsace.framework.common.annotation.PageQuery;
import com.alsace.service.demo.domain.User;
import com.alsace.service.demo.mapper.UserMapper;
import com.alsace.service.demo.serivce.UserService;
import com.github.pagehelper.Page;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Resource
  private UserMapper userMapper;

  @Override
  @PageQuery
  public Page<User> queryPage(User param) {
    return userMapper.queryPage(param);
  }
}
