package com.mobiquityinc.packer.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mobiquityinc.packer.exception.ApiException;
import com.mobiquityinc.packer.iterators.IteratorUtils;
import com.mobiquityinc.packer.iterators.LineItemIterator;
import com.mobiquityinc.packer.model.PackageItem;
import com.mobiquityinc.packer.processors.PackageListProcessor;
import com.mobiquityinc.packer.processors.RecordProcessor;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class PackageRecordReader.
 */
@Slf4j
@NoArgsConstructor
public class OptimalPackageRecordReader implements FileReaderDao, RecordReader<List<PackageItem>> {

	/** The input file. */
	protected File packageRecordsFile;

	/** The reader. */
	protected Reader reader;

	/** The record processor. */
	protected RecordProcessor<String, List<PackageItem>> recordProcessor;

	public OptimalPackageRecordReader(File packageRecordsFile) {

		this.packageRecordsFile = packageRecordsFile;
	}

	/**
	 * Return the Transformed iterator to read processed records
	 */
	@Override
	public Iterator<List<PackageItem>> iterator() {
		LineItemIterator lineItr = new LineItemIterator(reader);
		recordProcessor = new PackageListProcessor();
		return IteratorUtils.transformedIterator(lineItr, recordProcessor, this);
	}

	/**
	 * Created the empty list,this empty list will be filled with best items while
	 * processing
	 *
	 * @throws ApiException
	 *             the api exception
	 */
	@Override
	public List<PackageItem> create() throws ApiException {

		return new ArrayList<PackageItem>();
	}

	/**
	 * Open the stream of the file resource
	 *
	 * @throws ApiException
	 *             the api exception
	 */
	@Override
	public void open() {

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(packageRecordsFile);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			throw new ApiException("Failed to Open the reader");
		}

	}

	/**
	 * Open the stream of the file resource
	 *
	 * @throws ApiException
	 *             the api exception
	 */
	@Override
	public void close() {

		try {
			reader.close();
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new ApiException("Failed to Close the reader");
		}
	}

}
