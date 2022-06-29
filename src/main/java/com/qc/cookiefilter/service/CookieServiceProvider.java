package com.qc.cookiefilter.service;

import org.springframework.stereotype.Service;

@Service
public interface CookieServiceProvider {
	int processCookie(String[] args);
}
