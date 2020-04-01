package com.hafrans.tongrentang.wechat.common.vo;

import java.sql.Timestamp;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {
	
	private int errcode;
	
	private String errmsg;
	
	private Timestamp timestamp;
	
	private T data;
	
	public static <T> ResponseData<T> Builder(int code, String msg, Timestamp time, T data){
		ResponseData<T> resp = new ResponseData<T>(code, msg, time, data);
		return resp;
	}
	
	public static <T> ResponseData<T> Builder(int code, String msg, T data){
		ResponseData<T> resp = new ResponseData<T>(code,msg,Timestamp.from(Instant.now()),data);
		return resp;
	}
 	
}
