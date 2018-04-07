package br.com.saraiva;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * 
 * @author Thiago Araujo  - tjca1@hotmail.com - 07/04/2018
 *
 */
@Aspect
@Component
public class RestControllerAspecto {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(public * br.com.saraiva.controller.*Controller.*(..))")
    public void logBeforeRestCall(JoinPoint pjp) throws Throwable {
    	/*VERIFICANDO CONPORTAMENTOS DA APP QUANDO PRECISO*/
        log.info(":::::AOP ANTES REST :::::" + pjp);
    }
}
