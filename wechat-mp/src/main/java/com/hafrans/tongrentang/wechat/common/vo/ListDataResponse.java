package com.hafrans.tongrentang.wechat.common.vo;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListDataResponse<T> {
	
	private int errcode;
	
	private String errmsg;
	
	private Timestamp timestamp;
	
	private List<T> data;
	
}
