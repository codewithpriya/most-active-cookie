package com.qc.cookiefilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.quantcast.cookiefilter"})
public class MostActiveCookieApplication {

	public static void main(String[] args) {
		SpringApplication.run(MostActiveCookieApplication.class, args);
	}
}
