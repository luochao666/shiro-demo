package com.hx.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * IniRealm示例
 */

public class IniRealmTest {

    @Test
    public void testAuthentication(){
        IniRealm iniRealm = new IniRealm("classpath:user.ini");


        //1.构建SecurityManage环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();


        UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
        subject.login(token);

        //打印认证结果
        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        subject.checkRoles("admin");

        subject.checkPermission("user:delete");

    }
}
