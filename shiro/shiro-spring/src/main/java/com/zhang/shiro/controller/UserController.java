package com.zhang.shiro.controller;

import com.zhang.shiro.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyu
 * @create 2018-11-08 9:41
 **/
@RestController
public class UserController {

    @RequestMapping(value = "/subLogin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String subLogin(User user) {

        // 获取认证主体
        Subject subject = SecurityUtils.getSubject();

        // 获取前端传过来的用户名跟密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());

        try {
            // 记住我
            usernamePasswordToken.setRememberMe(user.isRememberMe());
            // 登录
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return e.getMessage();
        }

        if (subject.hasRole("admin")) {
            return "有admin权限";
        }
        return "没有admin权限";
    }

    //    @RequiresRoles("admin")
    @RequestMapping(value = "/testRole", method = RequestMethod.GET)
    public String testRole() {
        return "testRole success";
    }

    //    @RequiresRoles("admin1")
    @RequestMapping(value = "/testRole1", method = RequestMethod.GET)
    public String testRole1() {
        return "testRole1 success";
    }

    @RequestMapping(value = "/testPerms", method = RequestMethod.GET)
    public String testPerms() {
        return "testPerms success";
    }

    @RequestMapping(value = "/testPerms1", method = RequestMethod.GET)
    public String testPerms1() {
        return "testPerms1 success";
    }
}
