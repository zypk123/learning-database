package com.zhang.test;

import com.zhang.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author zhangyu
 * @create 2018-11-06 16:54
 **/
public class CustomRealmTest {

    @Test
    public void testAuthentiaction() {

        CustomRealm customRealm = new CustomRealm();

        // 1. 构建SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(customRealm);

        // 加密
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 加密类型
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 加密次数
        hashedCredentialsMatcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        // 2. 主体提交认证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        // 测试登录
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "abc123");
        subject.login(token);

        System.out.println("isAuthentiactioned:" + subject.isAuthenticated());

        // 校验角色
        subject.checkRole("admin");

        // 校验权限
        subject.checkPermission("user:delete");

    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("abc123","admin");
        System.out.println(md5Hash.toString());
    }
}
