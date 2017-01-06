import java.util.ArrayList;

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
        return outliers;
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
