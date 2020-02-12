package cn.lovingliu.aspect;


import cn.lovingliu.constant.UserRole;
import cn.lovingliu.controller.BaseController;
import cn.lovingliu.enums.ExceptionCodeEnum;
import cn.lovingliu.exception.SchoolParkException;
import cn.lovingliu.pojo.User;
import cn.lovingliu.util.CookieUtil;
import cn.lovingliu.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：LovingLiu
 * @Description: 权限拦截
 * @Date：Created in 2019-11-25
 */
@Aspect
@Component
@Slf4j
public class SchoolParkAuthorize {

    @Pointcut("execution(public * cn.lovingliu.controller.superadmin.*.* (..))")
    public void superAdminVerify(){}
    @Before("superAdminVerify()")
    public void doSuperAdminVerify() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStrInCookie = CookieUtil.get(request, BaseController.USER_COOKIE_KEY);
        // 1.判断是否登录
        if(userStrInCookie == null){
            throw new SchoolParkException(ExceptionCodeEnum.AUTHORIZE_NOLOGIN_FAIL);
        }
        // 2.判断权限是否足够
        User userInCookie = JsonUtils.jsonToPojo(userStrInCookie,User.class);
        if(userInCookie.getRole() != UserRole.SUPPER_ADMIN){
            throw  new SchoolParkException(ExceptionCodeEnum.AUTHORIZE_ROLE_FAIL);
        }
    }

    @Pointcut("execution(public * cn.lovingliu.controller.admin.*.* (..))")
    public void adminVerify(){}
    @Before("adminVerify()")
    public void doAdminVerify() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStrInCookie = CookieUtil.get(request, BaseController.USER_COOKIE_KEY);
        // 1.判断是否登录
        if(userStrInCookie == null){
            throw new SchoolParkException(ExceptionCodeEnum.AUTHORIZE_NOLOGIN_FAIL);
        }
        // 2.判断权限是否足够
        User userInCookie = JsonUtils.jsonToPojo(userStrInCookie,User.class);
        if(userInCookie.getRole() !=  UserRole.ADMIN){
            throw  new SchoolParkException(ExceptionCodeEnum.AUTHORIZE_ROLE_FAIL);
        }
    }

    @Pointcut("execution(public * cn.lovingliu.controller.user.*.* (..))")
    public void userVerify(){}
    @Before("userVerify()")
    public void doUserVerify() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStrInCookie = CookieUtil.get(request, BaseController.USER_COOKIE_KEY);
        // 1.判断是否登录
        if(userStrInCookie == null){
            throw new SchoolParkException(ExceptionCodeEnum.AUTHORIZE_NOLOGIN_FAIL);
        }
        // 2.判断权限是否足够
        User userInCookie = JsonUtils.jsonToPojo(userStrInCookie,User.class);
        if(userInCookie.getRole() != UserRole.USER){
            throw  new SchoolParkException(ExceptionCodeEnum.AUTHORIZE_ROLE_FAIL);
        }
    }
}
