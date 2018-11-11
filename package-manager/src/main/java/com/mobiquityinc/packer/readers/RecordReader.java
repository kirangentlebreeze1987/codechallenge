package com.mobiquityinc.packer.readers;

import com.mobiquityinc.packer.exception.ApiException;

/**
 * The Interface RecordReader.
 *
 * @param <T>
 *            the generic type
 */
public interface RecordReader<T> extends FileReaderDao, Iterable<T> {

	/**
	 * Creates the record for the reader
	 *
	 * @return the t the record
	 * @throws ApiException
	 *             the api exception
	 */
	T create() throws ApiException;

}
