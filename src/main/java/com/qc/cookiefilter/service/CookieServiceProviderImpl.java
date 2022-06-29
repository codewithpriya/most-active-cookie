package com.qc.cookiefilter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qc.cookiefilter.activecookie.ActiveCookie;
import com.qc.cookiefilter.exception.LogFileParserException;
import com.qc.cookiefilter.model.CommandLineInput;

import static com.qc.cookiefilter.service.Status.FAIL;
import static com.qc.cookiefilter.service.Status.SUCCESS;
import static com.qc.cookiefilter.validator.CommndLineValidator.parseCommandLineInput;

import java.io.IOException;
import java.util.Set;

@Service
public class CookieServiceProviderImpl implements CookieServiceProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(CookieServiceProvider.class);

	private ActiveCookie activeCookie;

	@Autowired
	public CookieServiceProviderImpl(ActiveCookie activeCookie) {
		this.activeCookie = activeCookie;
	}

	@Override
	public int processCookie(String[] args) {
		try {

			// Validate and parse command line input argument
			CommandLineInput commandLineInput = parseCommandLineInput(args);

			// Get most active cookie
			Set<String> latestCookie = activeCookie.getMostActiveCookies(commandLineInput);

			if (!latestCookie.isEmpty()) {
				System.out.println("\nMost active cookie : \n");
				latestCookie.forEach(System.out::println);
			} else {
				System.out.println("\n No cookie found on the date : " + args[3]);
			}
			return SUCCESS.getValue();

		} catch (IOException | LogFileParserException | RuntimeException e) {
			LOGGER.error("Program failed!", e);
		}
		return FAIL.getValue();
	}
}
