package com.alsace.service.demo.serivce;

import com.alsace.service.demo.domain.User;
import java.util.List;

public interface UserService {

  List<User> queryPage(User param);
}
