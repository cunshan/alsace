package com.example.web.controller;

import com.alsace.framework.common.basic.BaseController;
import com.example.service.user.domain.User;
import com.example.service.user.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

  @Autowired
  private UserService userService;

  @RequestMapping("/save")
  @ResponseBody
  public String save(User user){
    userService.create(user);
    return "success";
  }

}
