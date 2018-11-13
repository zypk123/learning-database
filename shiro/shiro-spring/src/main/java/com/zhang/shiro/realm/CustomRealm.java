package com.zhang.shiro.realm;

import com.zhang.shiro.dao.UserDao;
import com.zhang.shiro.vo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangyu
 * @create 2018-11-06 16:32
 **/
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 1. 从主体传过来的认证信息中，获取用户名
        String userName = (String) authenticationToken.getPrincipal();

        // 2. 通过用户名到数据中查询密码
        String passWord = getPassWordByUserName(userName);

        if (null == passWord) {
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName, passWord, "customRealm");

        // 加盐解密
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));

        return simpleAuthenticationInfo;
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String userName = (String) principalCollection.getPrimaryPrincipal();

        // 从数据库或者缓存中获取用户信息
        Set<String> roles = getRolesByUserName(userName);

        Set<String> permissionSet = new HashSet<String>();
        for (String role : roles) {
            Set<String> permission = getPermissionByRoleName(role);
            permissionSet.addAll(permission);
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissionSet);

        return simpleAuthorizationInfo;
    }

    /**
     * 模拟中数据库中获取密码
     *
     * @param userName
     */
    private String getPassWordByUserName(String userName) {
        User user = userDao.findPassWordByUserName(userName);

        if (null != user) {
            return user.getPassword();
        }
        return null;
    }

    /**
     * 从数据库中获取权限信息
     *
     * @param userName
     * @return
     */
    private Set<String> getPermissionByRoleName(String userName) {
        List<String> permissionList = userDao.findPermissionByRoleName(userName);
        return new HashSet<String>(permissionList);
    }

    /**
     * 从数据库中获取用户信息
     *
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        System.out.println("从数据库中获取数据...");
        List<String> roleList = userDao.findRolesByUserName(userName);
        return new HashSet<String>(roleList);
    }

}
