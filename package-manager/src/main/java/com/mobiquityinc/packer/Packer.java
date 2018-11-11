package com.mobiquityinc.packer;

import com.mobiquityinc.packer.exception.ApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Packer {

	private  static int exitCode = 1;
	
	public static void main(String[] args) {
		try {
			log.info("Packing Best Items Started");
			CommandLineArgsValidator validator = new CommandLineArgsValidator();
			validator.registerInitOptions(args);
			validator.parseInitOptions();
			validator.validateArgs();
			String filePath = validator.getOptionValue("mobiquity.package.file");
			Packer.pack(filePath);
			log.info("Packing Finished");
		} catch (ApiException ex) {
			log.error(ex.getMessage());
			exitCode = 2;
		}
		System.exit(exitCode);
	}

	/**
	 * Lists  out the best Items to Pack
	 *
	 * @param filePath the file path
	 * @return the exitcode
	 * @throws ApiException the api exception
	 */
	public static void pack(String filePath) throws ApiException {

		try {
			PackageManager packageManager = new PackageManager();
			packageManager.listBestPackages(filePath);
		}catch(ApiException ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}
}
