package com.example.homecode.dao;

import com.example.homecode.dto.UserAccess;
import org.springframework.data.repository.CrudRepository;

public interface UserAccessRepo extends CrudRepository<UserAccess, String> {
}
