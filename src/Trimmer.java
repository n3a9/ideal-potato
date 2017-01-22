import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trimmer {

	/**
	 * Will trim a data set to remove all outliers, based on the desired method
	 * (farther than average by 2*standard deviation or farther from closest
	 * quartile by 1.5*IQR). Utilizes a set of helper methods that help
	 * calculate and identify outliers to be removed.
	 *
	 * @precondition: parameter n is either 1 or 2.
	 *
	 * @param data:
	 *            an ArrayList of doubles containing the data set to be trimmed
	 *            of outliers
	 * @param n:
	 *            the chosen option to trim outliers (1 for IQR and 2 for
	 *            standard deviation)
	 */
	public static void dataTrimmer(ArrayList<Double> data, int n) {
		if (n == 1)
			findOutliersOne(data);
		else if (n == 2)
			findOutliersTwo(data);
		else
			throw new java.lang.Error("second parameter is not 1 or 2!!!!!!!!!!!!");
		
		System.out.println(data);
	}

	/**
	 * Will identify outliers based on the IQR. If the value is farther from the
	 * closest quartile by more than 1.5*IQR, then it is considered an outlier.
	 * Uses helper methods to identify the quartiles, as well as the IQR.
	 *
	 * Checks each value in the data set and compares the distance from both
	 * quartiles to 1.5*IQR. If the distance is more, then it is removed from
	 * the data set.
	 *
	 * @param data:
	 *            an arraylist of doubles containing the data set
	 */
	private static void findOutliersOne(ArrayList<Double> data) {
		double[] quartiles = findQuartiles(data);
		double lowerQuartile = quartiles[0];
		double upperQuartile = quartiles[1];
		double iqrDistance = (upperQuartile - lowerQuartile) * 1.5;
		// 1.5*IQR is the max distance from IQR before considered outlier

		for (int i = data.size() - 1; i >= 0; i--) {
			double x = data.get(i);
			if (Math.min(Math.abs(x - lowerQuartile), Math.abs(x - upperQuartile)) > iqrDistance)
				data.remove(i);
		}
	}

	/**
	 * Will identify the quartiles of the given data set. Will first sort the
	 * data, such that the data is in order and the median can be found. This
	 * does not affect the order of the returned data set of dataTrimmer, since
	 * this is only used to identify the IQR and quartiles.
	 *
	 * Then, it is determined if the number of elements in the data set is even
	 * or not. If it is even, then the list is split into a lower half and upper
	 * half directly from the middle. Otherwise, the list is split into a lower
	 * half and upper half excluding the middle element from both sides. Then,
	 * the median is found for both lists (the quartile) and put in an array to
	 * be returned.
	 *
	 * The array has only two elements, with quartile[0] being the first
	 * quartile and quartile[1] being the third quartile.
	 *
	 * @param data:
	 *            the data set of which the quartiles will be found.
	 *
	 * @return: an array, with the first element as the first quartile and the
	 *          second element as the third quartile.
	 */
	private static double[] findQuartiles(ArrayList<Double> data) {
		// sort the data (lowest to highest)
		Collections.sort(data);
		List<Double> lowerHalf = data.subList(0, (int) Math.floor(data.size() / 2.0 - 1));
		List<Double> upperHalf = data.subList((int) Math.ceil(data.size() / 2.0), data.size() - 1);

		return new double[] { findMedian(lowerHalf), findMedian(upperHalf) };
	}

	/**
	 * Will find the median of the given data set (assumed to be sorted). If the
	 * number of elements in the list is odd, then it will return the middle
	 * value. Otherwise, it will average the middle two elements.
	 *
	 * @precondition: the given data set is sorted
	 *
	 * @param: the
	 *             data set of which the median will be found
	 *
	 * @return: the median of the given data set
	 */
	private static double findMedian(List<Double> data) {
		if (data.size() % 2 != 0) // size is odd
			return data.get(data.size() / 2); // return middle value
		else
			return (data.get(data.size() / 2) + data.get((data.size() / 2) + 1)) / 2.0;
		// return average of two middle values
	}

	/**
	 * Will identify outliers based on standard deviation. If a value is more
	 * than 2*standard deviation from the average of the data set, it is
	 * considered an outlier.
	 *
	 * Checks each value in the data set and compares the distance from the
	 * average to 2*standard deviation. If the distance is more, then it is
	 * removed from the data set
	 *
	 * @param data:
	 *            an arraylist of doubles containing the data set
	 */
	private static void findOutliersTwo(ArrayList<Double> data) {
		double average = findAverage(data);
		double standardDeviationDistance = 2 * findStandardDeviation(data, average);

		for (int i = data.size() - 1; i >= 0; i--)
			if (Math.abs(data.get(i) - average) > standardDeviationDistance)
				data.remove(i);
	}

	/**
	 * Will find the standard deviation of the given data set. Goes through each
	 * element in the data set, subtracts the value from the average and squares
	 * it, and then divides it by the number of elements in the data set. Then
	 * takes the square root.
	 *
	 * @param data:
	 *            the data set of which the standard deviation will be found
	 *
	 * @return: the standard deviation of the data set
	 */
	private static double findStandardDeviation(ArrayList<Double> data, double average) {
		double standardDeviation = 0;
		for (double x : data)
			standardDeviation += Math.pow(x - average, 2);

		return Math.sqrt(standardDeviation / data.size());
	}

	/**
	 * Will find the average of the given data set.
	 *
	 * @param data:
	 *            the data set of which the average will be found
	 *
	 * @return: the average of the given data set.
	 */
	private static double findAverage(ArrayList<Double> data) {
		double sum = 0;
		for (double x : data)
			sum += x;

		return sum / data.size();
	}

}
