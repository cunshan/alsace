package com.example.service.user.serivce.impl;

import com.alsace.framework.common.annotation.LogModify;
import com.alsace.framework.common.annotation.PageQuery;
import com.alsace.framework.common.enums.LogModifyType;
import com.example.service.user.domain.User;
import com.example.service.user.mapper.UserMapper;
import com.example.service.user.repository.UserRepository;
import com.example.service.user.serivce.UserService;
import com.github.pagehelper.Page;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Resource
  private UserMapper userMapper;

  @Resource
  private UserRepository userRepository;

  @Override
  @PageQuery
  public Page<User> queryPage(User param) {
    return userMapper.queryPage(param);
  }

  @Override
  @LogModify(modifyType = LogModifyType.CREATE)
  public User create(User domain) {
    return userRepository.save(domain);
  }

  @Override
  public List<User> createBatch(List<User> list) {
    return (List<User>) userRepository.saveAll(list);
  }

  @Override
  public User update(User domain) {
    return userRepository.save(domain);
  }

  @Override
  public List<User> updateBatch(List<User> list) {
    return (List<User>) userRepository.saveAll(list);
  }

  @Override
  public User getById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }
}
