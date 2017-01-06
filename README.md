# ideal-potato
Data trimmer to remove outliers from a data set in Java. Outliers are determined by 2 methods.

1. IQR - if a value is farther than 1.5*IQR from the first or third quartile, it is considered an outlier.

2. Standard Deviation - if a value is farther than 2*standard deviation from the mean, then it is considered an outlier.

## Software
Built using Java. The CSV file is parsed and data sets are put into an ArrayList, in order for versatility and functionality of the data.

## Contributors
[Neeraj Aggarwal](http://neerajaggarwal.com)

[Patrick Zhong](https://github.com/18PatZ)
