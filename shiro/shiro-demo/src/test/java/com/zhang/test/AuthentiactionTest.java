package com.zhang.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 认证Test
 *
 * @author zhangyu
 * @create 2018-11-06 14:21
 **/
public class AuthentiactionTest {


    SimpleAccountRealm realm = new SimpleAccountRealm();

    @Before
    public void addRealm() {
        realm.addAccount("admin", "abc123","admin");
    }

    @Test
    public void testAuthentiaction() {

        // 1. 构建SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);

        // 2. 主体提交认证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        // 测试登录
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "abc123");
        subject.login(token);

        System.out.println("isAuthentiactioned:" + subject.isAuthenticated());

//        // 登出
//        subject.logout();
//        System.out.println("isAuthentiactioned:" + subject.isAuthenticated());

        // 校验角色
        subject.checkRole("admin");

    }
}
