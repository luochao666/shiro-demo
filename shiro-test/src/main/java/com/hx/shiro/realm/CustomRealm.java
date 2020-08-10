package com.hx.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {
    Map<String, String> usermap = new HashMap<String, String>(16);

    {
//        usermap.put("Mark","123456");
        usermap.put("Mark","283538989cef48f3d7d8a1c1bdf2008f");//123456 Mark用MD5加密后的编码
        super.setName("customRealm");
    }


    //用于授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        //从数据库/缓存中获取角色数据
        Set<String> roles = getRolesByUserName(username);
        Set<String> permissions = getPermissionsByUserName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    //获取角色权限
    private Set<String> getPermissionsByUserName(String username) {
        Set<String> sets = new HashSet<String>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }

    //获取角色数据
    private Set<String> getRolesByUserName(String username) {
        Set<String> sets = new HashSet<String>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }

    //用于认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.从主体传入的认证信息中，获取用户名
        String username = (String) authenticationToken.getPrincipal();
        
        //2.通过用户名到数据库中获取凭证
        String password = getPasswordByUserName(username);
        if (password == null){
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                "Mark", password, "customRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Mark"));//加盐修饰
        return simpleAuthenticationInfo;
    }

    private String getPasswordByUserName(String username) {
        //模拟数据库中查询凭证
        return usermap.get(username);
    }


    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","Mark");
        System.out.println(md5Hash.toString());
    }
}
