package com.hafrans.tongrentang.wechat.common.controller.advice;

import java.sql.Timestamp;
import java.time.Instant;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hafrans.tongrentang.wechat.common.status.PlainStatus;
import com.hafrans.tongrentang.wechat.common.status.exception.StatusException;
import com.hafrans.tongrentang.wechat.common.vo.ResponseData;

@ControllerAdvice
public class DefaultControllerAdvice {

	
	/**
	 * response status exception
	 * @param e
	 * @return
	 */
	@ExceptionHandler(StatusException.class)
	@ResponseBody
	public ResponseData<Object> handleStatusException(StatusException e){
		if (e.getCause() != null) {
			e.getCause().printStackTrace();
		}
		return ResponseData.<Object>Builder(e.getStatus(), e.getMessage(), Timestamp.from(Instant.now()), null);
	}
	
	/**
	 * handle Unauthenticated User
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	public ResponseData<Object> handleUnauthorizedException(UnauthorizedException e){
		return ResponseData.<Object>Builder(PlainStatus.STTAUS_ACCESS_DENIED, "access denied", Timestamp.from(Instant.now()), null);
	}
	
	/**
	 * handle other exception
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseData<Object> handleException(Exception e){
		e.printStackTrace();
		return ResponseData.<Object>Builder(PlainStatus.STATUS_FAILED, e.getMessage(), Timestamp.from(Instant.now()), null);
	}
	
}
