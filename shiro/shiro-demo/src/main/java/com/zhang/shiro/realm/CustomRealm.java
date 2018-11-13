package com.zhang.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangyu
 * @create 2018-11-06 16:32
 **/
public class CustomRealm extends AuthorizingRealm {

    Map<String, String> userMap = new HashMap<String, String>();

    {
        userMap.put("admin", "01c96006142142c9dcc78f5570c56cc0");
        super.setName("customRealm");
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
        Set<String> permissions = getPermissionByUserName(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    /**
     * 模拟从数据中获取权限信息
     *
     * @param userName
     * @return
     */
    private Set<String> getPermissionByUserName(String userName) {
        Set<String> set = new HashSet<String>();
        set.add("user:delete");
        set.add("user:update");
        return set;
    }

    /**
     * 模拟从数据中获取用户信息
     *
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {

        Set<String> set = new HashSet<String>();
        set.add("admin");
        set.add("user");
        return set;

    }

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

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("admin", passWord, "customRealm");

        // 加盐解密
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("admin"));

        return simpleAuthenticationInfo;
    }

    /**
     * 模拟中数据库中获取密码
     *
     * @param userName
     */
    private String getPassWordByUserName(String userName) {
        return userMap.get(userName);
    }

}
