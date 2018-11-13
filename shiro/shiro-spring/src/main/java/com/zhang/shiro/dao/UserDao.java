package com.zhang.shiro.dao;

import com.zhang.shiro.vo.User;

import java.util.List;

/**
 * @author zhangyu
 * @create 2018-11-08 11:49
 **/
public interface UserDao {
    User findPassWordByUserName(String userName);

    List<String> findRolesByUserName(String userName);

    List<String> findPermissionByRoleName(String roleName);
}
