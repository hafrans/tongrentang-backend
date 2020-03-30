package com.hafrans.tongrentang.wechat.common.formattingconversion;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class TimestampFormatter implements Formatter<Timestamp> {

	@Override
	public String print(Timestamp object, Locale locale) {
		
		LocalDateTime localDateTime = LocalDateTime.ofInstant(
															  Instant.ofEpochMilli(object.getTime()), 
															  ZoneId.of(TimeZone.getDefault().getID())
															  );
		
		
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	@Override
	public Timestamp parse(String text, Locale locale) throws ParseException {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp object = new Timestamp(format.parse(text).getTime());
		
		return object;
	}

}
