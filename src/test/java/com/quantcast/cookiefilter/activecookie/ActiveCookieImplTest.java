package com.quantcast.cookiefilter.activecookie;

import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.qc.cookiefilter.activecookie.ActiveCookie;
import com.qc.cookiefilter.activecookie.ActiveCookieImpl;
import com.qc.cookiefilter.exception.LogFileParserException;
import com.qc.cookiefilter.model.CommandLineInput;

@ExtendWith(SpringExtension.class)
public class ActiveCookieImplTest {

	private ActiveCookie activeCookieImpl;
	private PrintStream stdout = System.out;
	private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
		activeCookieImpl = new ActiveCookieImpl();
		PrintStream output = new PrintStream(byteArrayOutputStream);
		System.setOut(output);
	}

	@AfterEach
	public void tearDown() {
		System.setOut(stdout);
	}

	/**
	 * When file path and date are valid, output the most active cookie values
	 * 
	 * @throws IOException
	 * @throws LogFileParserException
	 */
	@Test
	public void getMostActiveCookies_ValidFilePathAndDate_PrintCookieStrings()
			throws LogFileParserException, IOException {
		CommandLineInput commandInput = new CommandLineInput("src/data/Cookie_Log.csv", parse("2018-12-09"));
		activeCookieImpl.getMostActiveCookies(commandInput);
		assertThat(byteArrayOutputStream.toString().contains("AtY0laUfhglK3lC7"));
		assertThat(!byteArrayOutputStream.toString().contains("SAZuXPGUrfbcn5UA"));
		assertThat(!byteArrayOutputStream.toString().contains("5UAVanZf6UtGyKVS"));
		assertThat(!byteArrayOutputStream.toString().contains("AtY0laUfhglK3lC7"));
	}

	/**
	 * When a specific date has more than one most active cookies, print all of them
	 * 
	 * @throws IOException
	 */
	@Test
	public void getMostActiveCookies_HasMoreThanOneMostActiveCookies_PrintAllOfThem()
			throws LogFileParserException, IOException {
		CommandLineInput commandInput = new CommandLineInput("src/data/Cookie_Log.csv", parse("2018-12-06"));
		activeCookieImpl.getMostActiveCookies(commandInput);
		assertThat(byteArrayOutputStream.toString().contains("8xYHIASHaBa79xzf"));
		assertThat(byteArrayOutputStream.toString().contains("1dSLJdsaDJLDsdSd"));
		assertThat(!byteArrayOutputStream.toString().contains("A8SADNasdNadBBda"));
	}

	/**
	 * When no specific date present in file, print nothing
	 * 
	 * @throws IOException
	 */
	@Test
	public void getMostActiveCookies_HasMoreThanOneMostActiveCookies_PrintNothing()
			throws LogFileParserException, IOException {
		CommandLineInput commandInput = new CommandLineInput("src/data/Cookie_Log.csv", parse("2011-12-06"));
		assertEquals(0, activeCookieImpl.getMostActiveCookies(commandInput).size());
	}

	/**
	 * When no specific date present in file, print nothing
	 * 
	 * @throws IOException
	 */
	@Test
	public void getMostActiveCookies_whenFileIsEmpty() throws LogFileParserException, IOException {
		CommandLineInput commandInput = new CommandLineInput("src/data/Empty_Log.csv", parse("2011-12-06"));
		assertEquals(0, activeCookieImpl.getMostActiveCookies(commandInput).size());
	}

	/** When file path is invalid, throw exception */
	@Test
	public void getMostActiveCookies_InvalidFilePath_ThrowException() {
		CommandLineInput commandInput = new CommandLineInput("/src/data/dummy.csv", parse("2018-12-09"));
		assertThatThrownBy(() -> activeCookieImpl.getMostActiveCookies(commandInput))
				.isInstanceOf(LogFileParserException.class);
	}
}
