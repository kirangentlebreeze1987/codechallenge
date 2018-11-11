package com.mobiquityinc.packer;

import java.io.File;
import java.util.List;

import com.mobiquityinc.packer.exception.ApiException;
import com.mobiquityinc.packer.model.PackageItem;
import com.mobiquityinc.packer.readers.OptimalPackageRecordReader;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class PackageManager. Lists out the best items to pack
 */
@Slf4j
public class PackageManager {

	/** The reader. */
	protected OptimalPackageRecordReader reader;

	/**
	 * List best packages.
	 *
	 * @param filePath
	 *            the file path
	 */
	public void listBestPackages(String filePath) throws ApiException {

		try {
			File file = new File(filePath);
			reader = new OptimalPackageRecordReader(file);
			reader.open();
			for (List<PackageItem> optimalPackages : reader) {
				String prefix = "";
				StringBuffer optimalIndexBuffer = new StringBuffer();
				if (optimalPackages.size() != 0) {
					for (PackageItem optimalPackagedItem : optimalPackages) {
						optimalIndexBuffer.append(prefix);
						prefix = ",";
						optimalIndexBuffer.append(optimalPackagedItem.getIndex());
					}
					System.out.println("Optimal Packaged Indexes" + optimalIndexBuffer.toString());
				}
			}
		} catch (ApiException ex) {
			log.error(ex.getMessage());
			throw ex;
		} finally {
			reader.close();
		}
	}

}
