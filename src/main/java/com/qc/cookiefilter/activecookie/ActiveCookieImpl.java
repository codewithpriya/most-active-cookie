package com.qc.cookiefilter.activecookie;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.qc.cookiefilter.exception.LogFileParserException;
import com.qc.cookiefilter.model.CommandLineInput;
import com.qc.cookiefilter.model.CookieItem;

@Component
public class ActiveCookieImpl implements ActiveCookie {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveCookieImpl.class);

	@Override
	public Set<String> getMostActiveCookies(CommandLineInput commandLineInput)
			throws LogFileParserException, IOException {

		List<CookieItem> cookieItemsList = getParsedFile(commandLineInput);

		// Filter out unmatched data
		List<CookieItem> cookieByDateAndTime = cookieItemsList.stream()
				.filter(x -> commandLineInput.getDateTime().isEqual(x.getTimestamp().toLocalDate()))
				.collect(Collectors.toList());

		if (cookieByDateAndTime.isEmpty()) {
			return new HashSet<>();
		}

		Map<String, LocalDateTime> resMap = new HashMap<>();
		cookieByDateAndTime.forEach(s -> resMap.put(s.getCookie(), s.getTimestamp()));

		boolean hasDuplicates = new HashSet<>(resMap.values()).size() != resMap.size();

		if (!hasDuplicates) {
			Optional<Entry<String, LocalDateTime>> maxEntry = resMap.entrySet().stream()
					.max(Comparator.comparing(Map.Entry::getValue));
			return Collections.singleton(maxEntry.get().getKey());
		} else {
			return resMap.keySet();
		}
	}

	private List<CookieItem> getParsedFile(CommandLineInput commandLineInput) throws LogFileParserException{
		List<CookieItem> cookieItemsList = null;
		try {
			cookieItemsList = LogFileParser.parseLogFile(commandLineInput.getLogFileName());
		} catch (IOException e1) {
			LOGGER.error("<---------------- Shutting down the application ----------------->");
			e1.printStackTrace();
			throw new LogFileParserException(e1);
		}
		return cookieItemsList;
	}
}
