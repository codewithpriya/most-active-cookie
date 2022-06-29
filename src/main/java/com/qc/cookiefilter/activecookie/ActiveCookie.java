package com.qc.cookiefilter.activecookie;

import java.io.IOException;
import java.util.Set;

import com.qc.cookiefilter.exception.LogFileParserException;
import com.qc.cookiefilter.model.CommandLineInput;

public interface ActiveCookie {
	Set<String> getMostActiveCookies(CommandLineInput commandLineInput) throws LogFileParserException, IOException;
}
