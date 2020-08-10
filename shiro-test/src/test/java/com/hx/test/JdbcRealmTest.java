package com.hx.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * JdbcRealm示例
 */
public class JdbcRealmTest {
    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
        dataSource.setUsername("hxdb");
        dataSource.setPassword("123456");
    }

    @Test
    public void testAuthentication(){
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);//设置查询权限开启

        //1.构建SecurityManage环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();


        UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
        subject.login(token);

        //打印认证结果
        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        subject.checkRoles("admin", "user");

        subject.checkPermission("user:select");

    }

}
