package com.zhang.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author zhangyu
 * @create 2018-11-06 14:48
 **/
public class IniRealmTest {

    @Test
    public void testAuthentiaction() {

        IniRealm realm = new IniRealm("classpath:user.ini");

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

        // 校验角色
        subject.checkRoles("admin");

        // 校验权限
        subject.checkPermissions("user:update");
    }
}
