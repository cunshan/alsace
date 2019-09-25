package com.alsace.service.demo.repository;

import com.alsace.service.demo.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
