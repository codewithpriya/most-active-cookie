package com.qc.cookiefilter.activecookie;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.bean.CsvToBeanBuilder;
import com.qc.cookiefilter.exception.LogFileParserException;
import com.qc.cookiefilter.model.CookieItem;

public class LogFileParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogFileParser.class);

	// Parse input and return the list of cookie entries from file
	public static List<CookieItem> parseLogFile(String fileName) throws LogFileParserException, IOException {
		FileReader fileReader = new FileReader(fileName);
		try {
			List<CookieItem> cookieItems = new CsvToBeanBuilder(fileReader).withType(CookieItem.class)
					.withFilter(line -> !line[0].contains("cookie")).build().parse(); // filter used to skip the header
			return cookieItems.isEmpty() ? logMessageReturnEmptyList() : cookieItems;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new LogFileParserException(e);
		} finally {
			fileReader.close();
		}
	}

	private static List<CookieItem> logMessageReturnEmptyList() {
		LOGGER.info("Invalid file path OR Empty file");
		return new ArrayList<>();
	}
}
