package com.mobiquityinc.packer;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.mobiquityinc.packer.exception.ApiException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class CommandLineArgsValidator Parses command line arguments,validate
 * arguments.
 */
@Slf4j
@Data
public class CommandLineArgsValidator {

	/** The options List */
	protected Options options;

	/** The command line. */
	protected CommandLine commandLine;

	/** The command line arguments. */
	protected String[] args;

	/** The opt mobquity file. */
	protected final String OPT_MOBQUITY_FILE = "mobiquity.package.file";

	/**
	 * Validate if the file exists.
	 */
	public void validateArgs() {
		log.info("Validaing Command Line Arguments");
		String filePath = getOptionValue(OPT_MOBQUITY_FILE);
		File file = new File(filePath);
		if (!file.exists()) {

			throw new ApiException(String.format("File does not exist at the given path %s", filePath));
		}
	}

	/**
	 * Register init options. Adds Option to the option list
	 * 
	 * @param args
	 *            the args
	 * 
	 */
	public void registerInitOptions(String[] args) {

		this.args = args;
		options = new Options();
		addOption(null, OPT_MOBQUITY_FILE, true, true, "File which contains items to pack");
	}

	/**
	 * Parses the init options.
	 * 
	 * @throws ApiException
	 *             when commandline arguments are not valid
	 */
	public void parseInitOptions() {
		log.info("Parsing Command Line Arguments");
		try {
			commandLine = new DefaultParser().parse(options, args, false);
		} catch (ParseException e) {
			log.error(e.getMessage());
			throw new ApiException(e.getMessage());
		}
	}

	/**
	 * Adds the option to the given options list.
	 *
	 * @param opt
	 *            the opt
	 * @param longOpt
	 *            the long opt
	 * @param hasArg
	 *            the has arg
	 * @param isRequired
	 *            the is required
	 * @param description
	 *            the description
	 * @param descriptionArgs
	 *            the description args
	 */
	public void addOption(String opt, String longOpt, boolean hasArg, boolean isRequired, String description,
			Object... descriptionArgs) {

		Option option = new Option(opt, longOpt, hasArg, String.format(description, descriptionArgs));
		option.setRequired(isRequired);
		options.addOption(option);
	}

	/**
	 * Gets the option value.
	 *
	 * @param opt
	 *            the opt
	 * @return the option value
	 */
	public String getOptionValue(String opt) {

		return commandLine.getOptionValue(opt);
	}
}
