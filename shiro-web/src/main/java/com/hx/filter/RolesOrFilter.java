package com.hx.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

//自定义filter
public class RolesOrFilter extends AuthorizationFilter {

    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        String[] roles = (String[]) o;
        if (roles == null || roles.length == 0){
            return true;
        }

        //有其中一个权限就算成功
        for (String role : roles){
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
