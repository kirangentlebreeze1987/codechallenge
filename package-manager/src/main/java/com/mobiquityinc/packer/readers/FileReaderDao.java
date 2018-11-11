package com.mobiquityinc.packer.readers;

import java.io.Closeable;

import com.mobiquityinc.packer.exception.ApiException;

/**
 * The Interface FileReaderDao.
 */
public interface FileReaderDao extends Closeable {

	/**
	 * Opens the stream of the file resource
	 *
	 * @throws ApiException
	 *             the api exception
	 */
	void open() throws ApiException;

	/**
	 * Closes the stream of the file resource
	 *
	 * @throws ApiException
	 *             the api exception
	 */
	void close() throws ApiException;

}
