package com.example.service.user.serivce.impl;

import com.alsace.framework.common.shiro.ShiroPrincipal;
import com.alsace.framework.common.shiro.ShiroUserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

@Service
public class ShiroUserServiceImpl implements ShiroUserService {

  @Override
  public Map<String, String> getPathDefinitionsMap() {
    Map<String, String> map = new HashMap<>();
    map.put("/do-login","anon");
//    map.put("/**","authc");
    return map;
  }

  @Override
  public List<String> getPermissionList(String loginAccount) {
    return new ArrayList<>();
  }

  @Override
  public ShiroPrincipal login(String loginAccount, char[] password) {
    ShiroPrincipal shiroPrincipal = new ShiroPrincipal();
    shiroPrincipal.setLoginAccount("aaa");
    shiroPrincipal.setPassword(new Md5Hash("11111", "user",2).toBase64());
    return shiroPrincipal;
  }
}
