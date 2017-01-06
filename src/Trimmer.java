import java.util.ArrayList;
import java.util.Collections;

public class Trimmer {

    //n represents the type of way to determine outliers
    //1 - more than 1.5 times IQR beyond first and third quartiles
    //2 - difference from average > 2 standard deviations
    public static ArrayList<Double> dataTrimmer(ArrayList<Double> data, int n) {
        ArrayList<Double> outliers = new ArrayList<>();
        ArrayList<Double> newData;
        if (n == 1) {
            outliers = findOutliersOne(data);
        } else if (n == 2) {
            outliers = findOutliersTwo(data);
        } else {
            throw new java.lang.Error("second parameter is not 1 or 2!!!!!!!!!!!!");
        }
        newData = removeOutliers(data, outliers);
        return newData;
    }

    private static ArrayList<Double> removeOutliers(ArrayList<Double> data, ArrayList<Double> outliers) {
        for (double x: outliers) {
            while (data.contains(x)) {
                data.remove(x);
            }
        }
        return data;
    }

    //implement IQR outlier identification
    private static ArrayList<Double> findOutliersOne(ArrayList<Double> data) {
        ArrayList<Double> outliers = new ArrayList<>(); //create empty array of doubles
        double iqrDistance= findIQR(data)*1.5;
        double[] quartiles = findQuartiles(data);
        double lowerQuartile = quartiles[0];
        double upperQuartile = quartiles[1];
        for (double x: data) {
            if ((Math.abs(x-lowerQuartile) > iqrDistance) || (Math.abs(x-upperQuartile) > iqrDistance)) {
                outliers.add(x);
            }
        }
        return outliers;
    }

    private static double findIQR(ArrayList<Double> data) {
        double[] quartile = findQuartiles(data);
        return quartile[1] - quartile[0];
    }

    //iqr[0] is the lower quartile
    //iqr[1] is the upper quartile
    private static double[] findQuartiles(ArrayList<Double> data) {
        //sort the data (lowest to highest)
        Collections.sort(data);
        double[] quartile = new double[2];
        ArrayList<Double> lowerHalf = new ArrayList<>();
        ArrayList<Double> upperHalf = new ArrayList<>();
        double median = findMedian(data);
        if (data.size()%2==0) { //if size of data is even
            lowerHalf = (ArrayList<Double>) data.subList(0, (data.size()/2)-1);
            upperHalf = (ArrayList<Double>) data.subList(data.size()/2, data.size()-1);
        } else {
            lowerHalf = (ArrayList<Double>) data.subList(0, (data.size()/2)-2);
            upperHalf = (ArrayList<Double>) data.subList(data.size()/2, data.size()-1);
        }
        quartile[0] = findMedian(lowerHalf);
        quartile[1] = findMedian(upperHalf);
        return quartile;
    }

    //precondition - data is presorted
    private static double findMedian(ArrayList<Double> data) {
        if (data.size()%2!=0) { //size is odd
            return data.get(data.size()/2); //return middle
        } else {
            return average(data.get(data.size()/2), data.get((data.size()/2)+1)); //return average of middle two values
        }
    }

    private static double average(double x, double y) {
        return (x+y)/2;
    }

    //based on 2 times more than standard deviation
    private static ArrayList<Double> findOutliersTwo(ArrayList<Double> data) {
        ArrayList<Double> outliers = new ArrayList<>(); //create empty list of doubles
        double average = findAverage(data);
        double standardDeviation = findStandardDeviation(data);
        for (double x: data) {
            if (Math.abs(x-average) > 2*standardDeviation) {
                outliers.add(x);
            }
        }
        return outliers;
    }

    private static double findAverage(ArrayList<Double> data) {
        double sum = 0;
        for (double x:data) {
            sum += x;
        }
        return sum/data.size();
    }

    private static double findStandardDeviation(ArrayList<Double> data) {
        double average = findAverage(data);
        double standardDeviation = 0;
        for (double x: data) {
            standardDeviation += (x - average)*(x - average);
        }
        return Math.sqrt(Math.abs(standardDeviation/data.size()));
    }

}
