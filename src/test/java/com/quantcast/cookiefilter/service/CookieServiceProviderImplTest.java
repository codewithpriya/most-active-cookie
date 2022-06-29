package com.quantcast.cookiefilter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qc.cookiefilter.activecookie.ActiveCookieImpl;
import com.qc.cookiefilter.exception.LogFileParserException;
import com.qc.cookiefilter.model.CommandLineInput;
import com.qc.cookiefilter.service.CookieServiceProviderImpl;
import com.qc.cookiefilter.service.Status;

@SpringBootTest(classes = CookieServiceProviderImpl.class)
public class CookieServiceProviderImplTest {

	@Autowired
	private CookieServiceProviderImpl cookieServiceProviderImpl;

	@MockBean
	ActiveCookieImpl activeCookieImpl;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void processCookie_whenEverythingGoesWell() throws LogFileParserException, IOException {
		Mockito.when(activeCookieImpl
				.getMostActiveCookies(new CommandLineInput("src/data/Cookie_Log.csv", LocalDate.parse("2011-12-06"))))
				.thenReturn(new HashSet<>());
		assertEquals(Status.SUCCESS.getValue(), cookieServiceProviderImpl
				.processCookie(new String[] { "-f", "src/data/Cookie_Log.csv", "-d", "2011-12-06" }));
	}

	@Test
	public void processCookie_whenEverythingDidNotGoWell() throws LogFileParserException, IOException {
		doThrow(new IOException(" File not Found ")).when(activeCookieImpl)
				.getMostActiveCookies(any(CommandLineInput.class));
		assertEquals(Status.FAIL.getValue(), cookieServiceProviderImpl
				.processCookie(new String[] { "-f", "src/dummyyy/Cookie_Log.csv", "-d", "2011-12-06" }));
	}
}
