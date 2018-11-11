package com.mobiquity.packer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mobiquityinc.packer.PackageManager;
import com.mobiquityinc.packer.exception.ApiException;
import com.mobiquityinc.packer.model.PackageItem;
import com.mobiquityinc.packer.readers.OptimalPackageRecordReader;

import lombok.extern.slf4j.Slf4j;

/**
 * Unit test for PackageManager App.
 */
@Slf4j
public class PackageManagerTest {

	@Test
	public void shouldReturnBestIndex() {

		try {
			String packageLine = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
			File tempFile = File.createTempFile("pattern", ".suffix");
			BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
			out.write(packageLine);
			out.close();
			OptimalPackageRecordReader packageReader = new OptimalPackageRecordReader(tempFile);
			packageReader.open();
			for (List<PackageItem> optimalPackages : packageReader) {
				Assert.assertEquals(optimalPackages.get(0).getIndex(), new Integer("4"));
				break;
			}
			packageReader.close();
		} catch (ApiException | IOException ex) {
			log.error(ex.getMessage());
		}
	}

	@Test(expected = ApiException.class)
	public void shouldThrowExceptionForExceedWeight() throws IOException {
		String packageLine = "500 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
		File tempFile = File.createTempFile("pattern", ".suffix");
		BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
		out.write(packageLine);
		out.close();
		PackageManager packageManager = new PackageManager();
		packageManager.listBestPackages(tempFile.getPath());
	}

	@Test(expected = ApiException.class)
	public void shouldThrowExceptionForInvalidparams() throws IOException {
		String packageLine = "(1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
		File tempFile = File.createTempFile("pattern", ".suffix");
		BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
		out.write(packageLine);
		out.close();
		PackageManager packageManager = new PackageManager();
		packageManager.listBestPackages(tempFile.getPath());
	}
}
