package com.mobiquityinc.packer.iterators;

import java.util.Iterator;

import com.mobiquityinc.packer.processors.RecordProcessor;
import com.mobiquityinc.packer.readers.RecordReader;

/**
 * The Class IteratorUtils.
 */
public class IteratorUtils {

	/**
	 * Transformed iterator.
	 *
	 * @param <I>
	 *            the generic type
	 * @param <O>
	 *            the generic type
	 * @param iterator
	 *            the iterator
	 * @param recordProcessor
	 *            the record processor
	 * @param recordReader
	 *            the record reader
	 * @return the iterator
	 */
	public static <I, O> Iterator<O> transformedIterator(final Iterator<? extends I> iterator,
			final RecordProcessor<? super I, O> recordProcessor, RecordReader<O> recordReader) {

		return new TransformIterator<>(iterator, recordProcessor, recordReader);

	}
}
