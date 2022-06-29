package com.qc.cookiefilter.service;

import static java.lang.System.exit;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CookieCommandLineRunner implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(CookieCommandLineRunner.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private CookieServiceProvider cookieServiceProvider;

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Most active cookie applicaion started with command line arguments : [{}]",
				Arrays.stream(args).collect(Collectors.joining(", ")));
		terminateProgram(cookieServiceProvider.processCookie(args));
	}

	private void terminateProgram(int exitCode) {
		exit(SpringApplication.exit(applicationContext));
	}
}
