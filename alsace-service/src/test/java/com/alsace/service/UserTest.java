package com.alsace.service;

import com.alsace.service.user.domain.User;
import com.alsace.service.user.serivce.UserService;
import com.alsace.service.user.serivce.impl.UserServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

  @Autowired
  private UserServiceImpl userService;


  @Test
  public void userTest(){
    User param = new User();
    param.setPageNum(1);
    param.setPageSize(10);
    List<User> userList = userService.queryPage(param);
    Assert.assertTrue(!userList.isEmpty());

  }

  @Test
  public void testCreate(){
    User param = new User();
    param.setUserName("aaa");
    userService.create(param);
  }
}
