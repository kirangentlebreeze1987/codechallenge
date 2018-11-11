package com.mobiquityinc.packer.helper;

import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.packer.exception.ApiException;
import com.mobiquityinc.packer.model.PackageItem;

import lombok.extern.slf4j.Slf4j;

/**
 * The Singleton Class BestPackageHelper helps in creating the best package from
 * the given list of items
 */
@Slf4j
public class OptimalPackageHelper {

	private static OptimalPackageHelper optimalPackageHelper;

	private OptimalPackageHelper() {
	}

	/**
	 * Gets the single instance of BestPackageHelper.
	 *
	 * @return single instance of BestPackageHelper
	 */
	public static OptimalPackageHelper getInstance() {

		if (optimalPackageHelper == null) {
			optimalPackageHelper = new OptimalPackageHelper();
		}
		return optimalPackageHelper;
	}

	/**
	 * Fill best package.
	 *
	 * @param weight
	 *            the weight
	 * @param itemList
	 *            the item list
	 * @param optimalChoice
	 *            the optimal choice
	 * @param numberOfItems
	 *            the number of items to be packed
	 * @return the double the price of the best item
	 * @throws ApiException
	 *             the api exception
	 */
	public double fillBestPackage(double weight, List<PackageItem> itemList, List<PackageItem> optimalChoice,
			int numberOfItems) throws ApiException {

		try {
			if (numberOfItems == 0 || weight == 0)
				return 0;

			if (itemList.get(numberOfItems - 1).getWeight() > weight) {
				List<PackageItem> subOptimalChoice = new ArrayList<>();
				double optimalCost = fillBestPackage(weight, itemList, subOptimalChoice, numberOfItems - 1);
				optimalChoice.addAll(subOptimalChoice);
				return optimalCost;
			} else {
				List<PackageItem> includeOptimalChoice = new ArrayList<>();
				List<PackageItem> excludeOptimalChoice = new ArrayList<>();
				double include_cost = itemList.get(numberOfItems - 1).getValue()
						+ fillBestPackage((weight - itemList.get(numberOfItems - 1).getWeight()), itemList,
								includeOptimalChoice, numberOfItems - 1);
				double exclude_cost = fillBestPackage(weight, itemList, excludeOptimalChoice, numberOfItems - 1);
				if (include_cost > exclude_cost) {
					optimalChoice.addAll(includeOptimalChoice);
					optimalChoice.add(itemList.get(numberOfItems - 1));
					return include_cost;
				} else {
					optimalChoice.addAll(excludeOptimalChoice);
					return exclude_cost;
				}
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new ApiException(ex);
		}
	}

	/**
	 * Creates the best packages.
	 *
	 * @param maxWeight
	 *            the max weight
	 * @param itemList
	 *            the item list
	 * @return the list
	 * @throws ApiException
	 *             the api exception
	 */
	public List<PackageItem> createBestPackages(double maxWeight, List<PackageItem> itemList) throws ApiException {

		try {
			List<PackageItem> bestPackageList = new ArrayList<>();
			fillBestPackage(maxWeight, itemList, bestPackageList, itemList.size());
			return bestPackageList;
		} catch (ApiException ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}
}