package com.zhang.service.impl;

import com.zhang.entity.Permission;
import com.zhang.entity.Role;
import com.zhang.entity.User;
import com.zhang.repository.RoleRepository;
import com.zhang.repository.UserRepository;
import com.zhang.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyu
 * @create 2018-11-12 14:44
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User addUser(Map<String, Object> map) {
        User user = new User();
        user.setName(String.valueOf(map.get("username")));
        user.setPassword(Integer.valueOf(String.valueOf(map.get("password"))));
        userRepository.save(user);
        return user;
    }

    @Override
    public Role addRole(Map<String, Object> map) {
        User user = userRepository.findOne(Long.valueOf(map.get("userId").toString()));

        Role role = new Role();
        role.setRoleName(map.get("roleName").toString());
        role.setUser(user);

        Permission permission1 = new Permission();
        permission1.setPermission("create");
        permission1.setRole(role);

        Permission permission2 = new Permission();
        permission2.setPermission("update");
        permission2.setRole(role);

        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(permission1);
        permissions.add(permission2);

        role.setPermissions(permissions);
        roleRepository.save(role);
        return role;
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}
