package com.hniu.travel.aop;

import com.hniu.travel.bean.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
@Aspect
public class LogAop {
    @Autowired
    private HttpServletRequest request;
    private final static Logger logger =  LoggerFactory.getLogger(LogAop.class);

    @Pointcut("execution(* com.hniu.travel.controller.backstage.*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint){
        //记录访问时间
        Date date = new Date();
        request.setAttribute("visitTime",date);

    }

    @After("pointCut()")
    public void doAfter(){
        Log log = new Log();

        Date visitTime = (Date) request.getAttribute("visitTime");

        Date now =new Date();
        //访问时长
        int executionTime =(int) (now.getTime() - visitTime.getTime());//访问时长
        //访问ip
        String ip = request.getRemoteAddr();
        //访问者
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User){
            String username = ((User) user).getUsername();
            log.setUsername(username);
        }
        //访问的路径
        String uri = request.getRequestURI();

        log.setExecutionTime(executionTime);
        log.setIp(ip);
        log.setUrl(uri);
        log.setVisitTime(visitTime);
        logger.info(log.toString());
    }
    @AfterThrowing(pointcut = "pointCut()",throwing = "ex")
    public void doAfterThrowing(Throwable ex){
        Log log = new Log();

        Date visitTime = (Date) request.getAttribute("visitTime");

        Date now =new Date();
        //访问时长
        int executionTime =(int) (now.getTime() - visitTime.getTime());//访问时长
        //访问ip
        String ip = request.getRemoteAddr();
        //访问者
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User){
            String username = ((User) user).getUsername();
            log.setUsername(username);
        }
        //访问的路径
        String uri = request.getRequestURI();

        log.setExecutionTime(executionTime);
        log.setIp(ip);
        log.setUrl(uri);
        log.setVisitTime(visitTime);
        //异常信息
        String message = ex.getMessage();
        log.setExecutionMessage(message);
        logger.info(log.toString());
    }
}
