package com.qc.cookiefilter.validator;

import static java.time.LocalDate.parse;

import java.time.format.DateTimeParseException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qc.cookiefilter.exception.LogFileParserException;
import com.qc.cookiefilter.model.CommandLineInput;

public class CommndLineValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommndLineValidator.class);

	private static final Option logFileName = new Option("f", false, "The cookie log file");
	private static final Option dateTime = new Option("d", false, "The specific date to get most active cookie");

	/** Validating command line inputs */
	public static CommandLineInput parseCommandLineInput(String[] args) throws LogFileParserException {
		CommandLineInput commandLineInput = new CommandLineInput();
		Options options = new Options();
		options.addOption(logFileName);
		options.addOption(dateTime);

		try {
			CommandLine cl = new DefaultParser().parse(options, args);

			if (cl.getArgList().size() != 2) {
				cookieCLIHelp(options);
				System.exit(-1);
			}

			if (cl.hasOption(logFileName.getOpt())) {
				commandLineInput.setLogFileName(cl.getArgs()[0].toString());
			}
			if (cl.hasOption(dateTime.getOpt())) {
				commandLineInput.setDateTime(parse(cl.getArgs()[1]));
			}
			return commandLineInput;
		} catch (DateTimeParseException | ParseException e) {
			LOGGER.error(e.getMessage());
			cookieCLIHelp(options);
			throw new LogFileParserException(e);
		}
	}

	/** Help message */
	private static void cookieCLIHelp(Options options) {
		HelpFormatter helpFormatter = new HelpFormatter();
		LOGGER.info(
				"App version [{}] Run Command :  java -jar target\\most-active-cookie-0.0.1-SNAPSHOT.jar -f [file name] -d [date] ",
				Math.class.getPackage().getImplementationVersion());
		helpFormatter.printHelp(" ", options);
	}
}
