package com.zhang.service;

import com.zhang.entity.Role;
import com.zhang.entity.User;

import java.util.Map;

/**
 * @author zhangyu
 * @create 2018-11-12 14:33
 **/
public interface LoginService {

    /**
     * 新增用户
     *
     * @param map
     * @return
     */
    User addUser(Map<String, Object> map);

    /**
     * 新增角色
     *
     * @param map
     * @return
     */
    Role addRole(Map<String, Object> map);

    /**
     * 通过姓名查找用户
     *
     * @param name
     * @return
     */
    User findByName(String name);

}
