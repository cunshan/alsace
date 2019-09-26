package com.alsace.service.user.repository;

import com.alsace.service.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
