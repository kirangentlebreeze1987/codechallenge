package com.mobiquityinc.packer.processors;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.mobiquityinc.packer.exception.ApiException;
import com.mobiquityinc.packer.helper.OptimalPackageHelper;
import com.mobiquityinc.packer.model.PackageItem;

/**
 * The Class PackageListProcessor process the items and gives the best items to
 * pack
 */
public class PackageListProcessor implements RecordProcessor<String, List<PackageItem>> {

	/** The max packs weight. */
	private final Integer MAX_PACKS_WEIGHT = 100;

	/** The max packs size. */
	private final Integer MAX_PACKS_SIZE = 15;

	/** The best packagehelper,a singleton helps in getting the best packages */
	protected OptimalPackageHelper bestPackagehelper;

	/** The line number will be increamented on every call to the process method */
	protected AtomicInteger lineNumber = new AtomicInteger(0);

	/**
	 * Process source and returns the list with best items in it
	 *
	 * @param input
	 *            the line from the file
	 * @param packageList
	 *            empty list from the reader will be filled with best items to be
	 *            packed
	 * @return the list with best items
	 * @throws ApiException
	 *             if there is any exception could be number format or constraint
	 *             violation wrapped under Apiexception again or any other runtime
	 *             exception
	 */

	@SuppressWarnings("static-access")
	@Override
	public List<PackageItem> process(String input, List<PackageItem> packageList) throws ApiException {

		try {
			String[] splittedInput = input.split(":");
			int maxWeight = Integer.parseInt(splittedInput[0].trim());
			String[] items = splittedInput[1].trim().split("\\s+");
			lineNumber.incrementAndGet();
			validateSource(maxWeight, items.length, splittedInput.length, lineNumber.get());
			for (String item : items) {
				String[] pack = item.trim().replace("(", "").replace(")", "").split(",");
				PackageItem packageBean = new PackageItem();
				packageBean.setIndex(Integer.parseInt(pack[0].trim()));
				packageBean.setWeight(new Double(pack[1].trim()));
				packageBean.setValue(new Double(pack[2].substring(1)));
				packageList.add(packageBean);
			}
			bestPackagehelper = bestPackagehelper.getInstance();
			List<PackageItem> bestPackages = bestPackagehelper.createBestPackages(maxWeight, packageList);
			return bestPackages;
		} catch (Exception ex) {

			throw new ApiException(ex);
		}
	}

	/**
	 * Validate source.
	 *
	 * @param maxWeight
	 *            the max weight
	 * @param maxPacks
	 *            the max packs
	 * @param argsLength
	 *            the args length
	 * @param line
	 *            the line
	 * @param lineNumber
	 *            the line number
	 * @throws ApiException
	 *             if maxweight exceeds limit
	 * @throws ApiException
	 *             if maxPacks exceeds limit
	 * @throws ApiException
	 *             if input is invalid
	 */
	private void validateSource(int maxWeight, int maxPacks, int argsLength, Integer lineNumber) throws ApiException {

		if (argsLength != 2) {
			throw new ApiException(String.format("Invalid input source,Error Occured at Line Number %d", lineNumber));
		}

		if (maxWeight > MAX_PACKS_WEIGHT) {

			throw new ApiException(String
					.format("Invalid input source,Total weight Limit Exceeded at Line Item Number %d", lineNumber));
		}
		if (maxPacks > MAX_PACKS_SIZE) {

			throw new ApiException(
					String.format("Invalid input source,Total pack Limit Exceeded at Line Item Number %d", lineNumber));
		}
	}
}
