package com.qc.cookiefilter.exception;

/** Custom exception for cookie log file parser */
public class LogFileParserException extends Exception {
	public LogFileParserException(Throwable error) {
		super(error);
	}
}