import java.util.ArrayList;
import java.util.Collections;

public class Trimmer {
    
    /**
     * Will trim a data set to remove all outliers, based on the desired method (farther than average by 2*standard
     * deviation or farther from closest quartile by 1.5*IQR). Utilizes a set of helper methods that help calculate
     * and identify outliers to be removed.
     *
     * @precondition: parameter n is either 1 or 2.
     *
     * @param data: an ArrayList of doubles containing the data set to be trimmed of outliers
     * @param n: the chosen option to trim outliers (1 for IQR and 2 for standard deviation)
     *
     * @return: an ArrayList of doubles containing the trimmed data set
     */
    public static ArrayList<Double> dataTrimmer(ArrayList<Double> data, int n) {
        ArrayList<Double> outliers = new ArrayList<>();
        ArrayList<Double> newData;
        if (n == 1)
        {
            outliers = findOutliersOne(data);
        }
        else if (n == 2)
        {
            outliers = findOutliersTwo(data);
        }
        else
        {
            throw new java.lang.Error("second parameter is not 1 or 2!!!!!!!!!!!!"); //did not meet precondition
        }
        newData = removeOutliers(data, outliers);
        return newData;
    }
    
    /**
     * Removes all instances of each outlier in the array from the data set. Continues to check if the element
     * is present in the data set and removes it until all instances are removed.
     *
     * @param data: an ArrayList of doubles containing the data set
     * @param outliers: an ArrayList containing doubles that have been identified as outliers
     *
     * @return: the dataset with all instances of values from the array removed
     */
    private static ArrayList<Double> removeOutliers(ArrayList<Double> data, ArrayList<Double> outliers) {
        for (double x: outliers)
        {
            while (data.contains(x)) //outlier is still present in data
            {
                data.remove(x); //remove the outlier
            }
        }
        return data;
    }
    
    /**
     * Will identify outliers based on the IQR. If the value is farther from the closest quartile by
     * more than 1.5*IQR, then it is considered an outlier. Uses helper methods to identify the quartiles, as
     * well as the IQR.
     *
     * Checks each value in the data set and compares the distance from both quartiles to 1.5*IQR. If the distance
     * is more, then it is added into an ArrayList containing all the outliers.
     *
     * @param data: an arraylist of doubles containing the data set
     *
     * @return: an ArrayList containing outliers in the data set
     */
    private static ArrayList<Double> findOutliersOne(ArrayList<Double> data) {
        ArrayList<Double> outliers = new ArrayList<>();
        double iqrDistance= findIQR(data)*1.5; //1.5*IQR is the max distance from quartile before considered outlier
        double[] quartiles = findQuartiles(data);
        double lowerQuartile = quartiles[0];
        double upperQuartile = quartiles[1];
        for (double x: data)
        {
            if ((Math.abs(x-lowerQuartile) > iqrDistance) || (Math.abs(x-upperQuartile) > iqrDistance))
            {
                outliers.add(x);
            }
        }
        return outliers;
    }
    
    /**
     * Will return the IQR of the data set(the difference of the first and third quartiles).
     *
     * @param data: the data set of which the IQR will be found
     *
     * @return: the IQR of the data set.
     */
    private static double findIQR(ArrayList<Double> data) {
        double[] quartile = findQuartiles(data);
        return quartile[1] - quartile[0];
    }
    
    /**
     * Will identify the quartiles of the given data set. Will first sort the data, such that the data
     * is in order and the median can be found. This does not affect the order of the returned data set of
     * dataTrimmer, since this is only used to identify the IQR and quartiles.
     *
     * Then, it is determined if the number of elements in the data set is even or not. If it is even, then
     * the list is split into a lower half and upper half directly from the middle. Otherwise, the list is split
     * into a lower half and upper half excluding the middle element from both sides. Then, the median is found for
     * both lists (the quartile) and put in an array to be returned.
     *
     * The array has only two elements, with quartile[0] being the first quartile and quartile[1] being the third
     * quartile.
     *
     * @param data: the data set of which the quartiles will be found.
     *
     * @return: an array, with the first element as the first quartile and the second element as the third quartile.
     */
    private static double[] findQuartiles(ArrayList<Double> data) {
        //sort the data (lowest to highest)
        Collections.sort(data);
        double[] quartile = new double[2];
        ArrayList<Double> lowerHalf = new ArrayList<>();
        ArrayList<Double> upperHalf = new ArrayList<>();
        double median = findMedian(data);
        if (data.size()%2==0) //if size of data is even
        {
            lowerHalf = (ArrayList<Double>) data.subList(0, (data.size()/2)-1);
            upperHalf = (ArrayList<Double>) data.subList(data.size()/2, data.size()-1);
        }
        else //size of data is odd
        {
            lowerHalf = (ArrayList<Double>) data.subList(0, (data.size()/2)-2); //exclude the middle element
            upperHalf = (ArrayList<Double>) data.subList(data.size()/2, data.size()-1);
        }
        quartile[0] = findMedian(lowerHalf); //first quartile
        quartile[1] = findMedian(upperHalf); //third quartile
        return quartile;
    }
    
    /**
     * Will find the median of the given data set (assumed to be sorted). If the number of elements in the list is
     * odd, then it will return the middle value. Otherwise, it will average the middle two elements.
     *
     * @precondition: the given data set is sorted
     *
     * @param: the data set of which the median will be found
     *
     * @return: the median of the given data set
     */
    private static double findMedian(ArrayList<Double> data) {
        if (data.size()%2!=0) //size is odd
        {
            return data.get(data.size()/2); //return middle value
        }
        else
        {
            return average(data.get(data.size()/2), data.get((data.size()/2)+1)); //return average of middle two values
        }
    }
    
    /**
     * Finds the average of two doubles.
     *
     * @param x: the first double to be averaged
     * @param y: the second double to be averaged
     *
     * @return: the average of x and y
     */
    private static double average(double x, double y) {
        return (x+y)/2;
    }
    
    /**
     * Will identify outliers based on standard deviation. If a value is more than 2*standard deviation from the
     * average of the data set, it is considered an outlier.
     *
     * Checks each value in the data set and compares the distance from the average to 2*standard deviation. If the distance
     * is more, then it is added into an arraylist containing all the outliers.
     *
     * @param data: an arraylist of doubles containing the data set
     *
     * @return: an arraylist containing outliers in the data set
     */
    private static ArrayList<Double> findOutliersTwo(ArrayList<Double> data) {
        ArrayList<Double> outliers = new ArrayList<>();
        double average = findAverage(data);
        double standardDeviationDistance = 2*findStandardDeviation(data);
        for (double x: data)
        {
            if (Math.abs(x-average) > standardDeviationDistance)
            {
                outliers.add(x);
            }
        }
        return outliers;
    }
    
    /**
     * Will find the standard deviation of the given data set. Goes through each element in the data set, subtracts the value
     * from the average and squares it, and then divides it by the number of elements in the data set. Then takes the square
     * root.
     *
     * @param data: the data set of which the standard deviation will be found
     *
     * @return: the standard deviation of the data set
     */
    private static double findStandardDeviation(ArrayList<Double> data) {
        double average = findAverage(data);
        double standardDeviation = 0;
        for (double x: data)
        {
            standardDeviation += (x - average)*(x - average);
        }
        return Math.sqrt(standardDeviation/data.size());
    }
    
    /**
     * Will find the average of the given data set.
     *
     * @param data: the data set of which the average will be found
     *
     * @return: the average of the given data set.
     */
    private static double findAverage(ArrayList<Double> data) {
        double sum = 0;
        for (double x:data)
        {
            sum += x;
        }
        return sum/data.size();
    }
    
}
