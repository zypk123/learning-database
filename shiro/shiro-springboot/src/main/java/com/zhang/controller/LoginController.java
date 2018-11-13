package com.zhang.controller;

import com.zhang.entity.Role;
import com.zhang.entity.User;
import com.zhang.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhangyu
 * @create 2018-11-12 15:01
 **/
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody Map map) {
        // 添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                map.get("username").toString(),
                map.get("password").toString());
        // 进行验证，这里可以捕获异常，然后返回对应信息
        subject.login(usernamePasswordToken);
        return "login";
    }

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "logout";
    }

    /**
     * 错误页面展示
     *
     * @return
     */
    @RequestMapping(value = "/error", method = RequestMethod.POST)
    public String error() {
        return "error ok!";
    }

    /**
     * 新增用户(初始化数据)
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestBody Map<String, Object> map) {
        User user = loginService.addUser(map);
        return "addUser is ok! \n" + user;
    }

    /**
     * 新增角色(角色初始化)
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public String addRole(@RequestBody Map<String, Object> map) {
        Role role = loginService.addRole(map);
        return "addRole is ok! \n" + role;
    }

    /**
     * 演示注解使用
     *
     * @return
     */
    @RequiresRoles("admin")
    @RequiresPermissions("create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "Create success!";
    }
}
