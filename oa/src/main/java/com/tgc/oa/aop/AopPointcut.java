package com.tgc.oa.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yezi
 * @date 2019/07/01
 */
@Aspect
@Component
public class AopPointcut {
    public static final Logger log = LoggerFactory.getLogger(AopPointcut.class);

    /**
     * 定义切入点
     */
    @Pointcut("execution(public * com.tgc.oa.controller..*.*(..))")
    public void controllerLog(){}

    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("url:"+request.getRequestURI().toString());
        log.info("Ip:"+request.getRemoteAddr());

    }

}
