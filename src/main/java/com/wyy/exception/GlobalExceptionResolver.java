package com.wyy.exception;

import com.alibaba.fastjson.JSON;
import com.wyy.dto.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 错误信息统一处理
 * 对未处理的错误信息做一个统一处理
 * @author yingjun
 *
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@ResponseBody
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		LOG.error("访问" + request.getRequestURI() + " 发生错误, 错误信息:" + ex.getMessage());
		//这里有2种选择
		//跳转到定制化的错误页面
	    /*ModelAndView error = new ModelAndView("error");
		error.addObject("exMsg", ex.getMessage());
		error.addObject("exType", ex.getClass().getSimpleName().replace("\"", "'"));*/
		//返回json格式的错误信息
		try {
			PrintWriter writer = response.getWriter();
			BaseResult<String> result=new BaseResult(false, ex.getMessage());
			writer.write(JSON.toJSONString(result));
			writer.flush();
		} catch (Exception e) {
			LOG.error("Exception:",e);
		}
		return null;
	}
	//
	// valid exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public BaseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		String errorMesssage = "Invalid Request:";

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errorMesssage += fieldError.getDefaultMessage() + ", ";
		}

		System.out.println(bindingResult.getFieldError().getDefaultMessage());
		BaseResult response = new BaseResult(false,errorMesssage);
		return response;
	}
	// JSON convert exception
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public BaseResult handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		BaseResult response = new BaseResult(false,"json convert failure!");
		return response;
	}
	

}
