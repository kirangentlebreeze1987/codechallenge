package com.mobiquityinc.packer.iterators;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Class LineItemIterator.
 */
public class LineItemIterator implements Iterator<String>, Closeable {

	/** The reader that is being read. */
	private final BufferedReader bufferedReader;
	/** The current line. */
	private String cachedLine;
	/** A flag indicating if the iterator has been fully read. */
	private boolean finished = false;

	/**
	 * Constructs an iterator of the lines for a Reader.
	 *
	 * @param reader
	 *            the Reader to read from, not null
	 * @throws IllegalArgumentException
	 *             if the reader is null
	 */
	public LineItemIterator(final Reader reader) throws IllegalArgumentException {
		if (reader == null) {
			throw new IllegalArgumentException("Reader must not be null");
		}
		if (reader instanceof BufferedReader) {
			bufferedReader = (BufferedReader) reader;
		} else {
			bufferedReader = new BufferedReader(reader);
		}
	}

	/**
	 * Indicates whether the Reader has more lines.
	 * 
	 * @return true if the Reader has more lines
	 * @throws IllegalStateException
	 *             if an IO exception occurs
	 */
	@Override
	public boolean hasNext() {
		if (cachedLine != null) {
			return true;
		} else if (finished) {
			return false;
		} else {
			try {
				while (true) {
					final String line = bufferedReader.readLine();
					if (line == null) {
						finished = true;
						return false;
					} else {
						cachedLine = line;
						return true;
					}
				}
			} catch (final IOException ioe) {
				try {
					close();
				} catch (final IOException e) {
					ioe.addSuppressed(e);
				}
				throw new IllegalStateException(ioe);
			}
		}
	}

	/**
	 * Returns the next line in the wrapped Reader.
	 *
	 * @return the next line from the input
	 * @throws NoSuchElementException
	 *             if there is no line to return
	 */
	@Override
	public String next() {
		return nextLine();
	}

	/**
	 * Returns the next line in the wrapped Reader
	 * 
	 * @return the next line from the input
	 * @throws NoSuchElementException
	 *             if there is no line to return
	 */
	public String nextLine() {
		if (!hasNext()) {
			throw new NoSuchElementException("No more lines");
		}
		final String currentLine = cachedLine;
		cachedLine = null;
		return currentLine;
	}

	/**
	 * Closes the underlying Reader.
	 * 
	 * @throws IOException
	 *             if closing the underlying Reader fails.
	 */
	@Override
	public void close() throws IOException {
		finished = true;
		cachedLine = null;
		if (this.bufferedReader != null) {
			this.bufferedReader.close();
		}
	}

	/**
	 *
	 * @throws UnsupportedOperationException
	 *             always
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove unsupported on LineIterator");
	}
}