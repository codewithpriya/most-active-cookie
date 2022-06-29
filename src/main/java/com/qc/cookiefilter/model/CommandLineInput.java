package com.qc.cookiefilter.model;

import java.time.LocalDate;

public class CommandLineInput {
	private String logFileName; // f: filename
	private LocalDate dateTime; // d: selected date

	public String getLogFileName() {
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public LocalDate getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDate dateTime) {
		this.dateTime = dateTime;
	}

	public CommandLineInput(String logFileName, LocalDate dateTime) {
		super();
		this.logFileName = logFileName;
		this.dateTime = dateTime;
	}

	public CommandLineInput() {
	}

}
