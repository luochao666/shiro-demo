package com.hx.dao;

import com.hx.vo.User;

import java.util.List;

public interface UserDao {
    User getUserByUserName(String username);

    List<String> queryRolesByUserName(String username);
}
