package com.mobiquityinc.packer.processors;

import com.mobiquityinc.packer.exception.ApiException;

/**
 * The Interface RecordProcessor.
 *
 * @param <I>
 *            the generic type
 * @param <O>
 *            the generic type
 */
public interface RecordProcessor<I, O> {

	/**
	 * Process the record
	 *
	 * @param input
	 *            the input
	 * @param output
	 *            the empty object from the reader,fill the object with data while
	 *            processing
	 * @return the o output filled record
	 * @throws ApiException
	 *             the api exception
	 */
	O process(I input, O output) throws ApiException;
}
