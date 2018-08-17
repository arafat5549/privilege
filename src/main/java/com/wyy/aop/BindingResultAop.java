package com.wyy.aop;

import com.wyy.dto.BaseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yingjun
 *
 * 采用AOP的方式处理参数问题。
 */
@Component
@Aspect
public class BindingResultAop {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.wyy.controller.*.*(..))")
    public void aopMethod(){}

    @Around("aopMethod()")
    public Object  around(ProceedingJoinPoint joinPoint) throws Throwable{
        LOG.info("before method invoking!");
        BindingResult bindingResult = null;
        for(Object arg:joinPoint.getArgs()){
            if(arg instanceof BindingResult){
                bindingResult = (BindingResult) arg;
            }
        }
        if(bindingResult != null){
            if(bindingResult.hasErrors()){
                String errorInfo="["+bindingResult.getFieldError().getField()+"]"+bindingResult.getFieldError().getDefaultMessage();
                return new BaseResult<Object>(false, errorInfo);
            }
        }
        return joinPoint.proceed();
    }
//    //
//    // JSON convert exception
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    @ResponseBody
//    public ResponseBean handleHttpMessageNotReadableException(
//            HttpMessageNotReadableException ex) {
//        ResponseBean response = new ResponseBean();
//        response.setErrcode("-22");
//        response.setErrmsg("json convert failure!");
//        return response;
//    }
}
