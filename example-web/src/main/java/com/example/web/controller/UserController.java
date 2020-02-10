package com.example.web.controller;

import com.alsace.framework.common.basic.BaseController;
import com.example.service.user.domain.User;
import com.example.service.user.serivce.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
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

  @RequestMapping("/do-login")
  @ResponseBody
  public String doLogin(User user){
    UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
    SecurityUtils.getSubject().login(token);
    return "success";
  }

  @RequestMapping("/login")
  @ResponseBody
  public String login(){
    return "login";
  }

}
