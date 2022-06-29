package com.qc.cookiefilter.model;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

public class CookieItem {

	@CsvBindByPosition(position = 0) // file's first column(cookie name)
	private String cookie;

	@CsvBindByPosition(position = 1) // file's second column(TimeStamp)
	@CsvDate(value = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private LocalDateTime timestamp;

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
