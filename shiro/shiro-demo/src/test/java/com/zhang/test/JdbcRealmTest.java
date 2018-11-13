package com.zhang.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author zhangyu
 * @create 2018-11-06 15:12
 **/
public class JdbcRealmTest {


    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("pass");
    }

    @Test
    public void testAuthentiaction() {

        JdbcRealm jdbcRealm = new JdbcRealm();
        // 设置数据源
        jdbcRealm.setDataSource(dataSource);
        // 设置权限开关，默认是false
        jdbcRealm.setPermissionsLookupEnabled(true);

        // 自定义sql查询
        String sql = "select pass_word from test_user where user_name = ?";
        jdbcRealm.setAuthenticationQuery(sql);

        // 1. 构建SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(jdbcRealm);

        // 2. 主体提交认证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        // 测试登录
        UsernamePasswordToken token = new UsernamePasswordToken("Mary", "123456");
        subject.login(token);

//        System.out.println("isAuthentiactioned:" + subject.isAuthenticated());
//
//        subject.checkRoles("admin");
//
//        subject.checkPermission("user:select");


    }


}
