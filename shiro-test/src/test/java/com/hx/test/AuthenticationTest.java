package com.hx.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Shiro认证 与 授权
 */

public class AuthenticationTest {
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("Mark","123456", "admin","user");
    }


    @Test
    public void testAuthentication(){

        //1.构建SecurityManage环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();


        UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
        subject.login(token);

        //打印认证结果
        System.out.println("isAuthenticated:" + subject.isAuthenticated());


        //校验角色
        subject.checkRoles("admin", "user");

//        //退出后 认证不会通过
//        subject.logout();
//        System.out.println("isAuthenticated:" + subject.isAuthenticated());
    }
}
