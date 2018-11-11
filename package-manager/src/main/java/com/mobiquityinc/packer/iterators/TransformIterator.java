package com.mobiquityinc.packer.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.mobiquityinc.packer.exception.ApiException;
import com.mobiquityinc.packer.processors.RecordProcessor;
import com.mobiquityinc.packer.readers.RecordReader;

/**
 * The Class TransformIterator.
 *
 * @param <I>
 *            the generic type
 * @param <O>
 *            the generic type
 */
public class TransformIterator<I, O> implements Iterator<O> {

	/** The backing iterator. */
	private Iterator<? extends I> backingIterator;

	/** The Record Processor being used to process records */
	private RecordProcessor<? super I, O> transformer;

	/** The record reader that is being read */
	private RecordReader<O> recordReader;

	/**
	 * Instantiates a new transform iterator.
	 *
	 * @param backingIterator
	 *            the backing iterator
	 * @param transformer
	 *            the transformer
	 * @param recordReader
	 *            the record reader
	 */
	public TransformIterator(final Iterator<? extends I> backingIterator, RecordProcessor<? super I, O> transformer,
			final RecordReader<O> recordReader) {
		super();
		this.backingIterator = backingIterator;
		this.transformer = transformer;
		this.recordReader = recordReader;
	}

	/**
	 * Indicates whether backing iterator has more records.
	 * 
	 * @return true if the backing iterator has more records
	 */

	@Override
	public boolean hasNext() {
		return backingIterator.hasNext();
	}

	/**
	 * @return the next processed record
	 * @throws NoSuchElementException
	 *             if there is no line to return
	 */
	@Override
	public O next() {

		return transform(backingIterator.next());
	}

	/**
	 * Process the record using Record Processor
	 *
	 * @param source
	 *            the source
	 * @return the processed record
	 */
	protected O transform(final I source) {
		try {
			O outputRecord = this.recordReader.create();
			return transformer.process(source, outputRecord);
		} catch (Exception e) {
			throw new ApiException(e);
		}
	}

}
