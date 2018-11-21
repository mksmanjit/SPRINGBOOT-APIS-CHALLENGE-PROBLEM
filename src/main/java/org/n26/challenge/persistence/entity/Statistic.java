package org.n26.challenge.persistence.entity;

/**
 * This is the POJO class for holding MetaData information of statistic.
 * 
 * @author manjit kumar
 *
 */
public class Statistic {

    /**
     * holds the summation of all statistics.
     */
    private double sum;
    /**
     * holds the average statistics.
     */
    private double avg;
    /**
     * holds the maximum number in statistics.
     */
    private double max;
    /**
     * holds the minimum number in statistics.
     */
    private double min;
    /**
     * holds the total count of statistics.
     */
    private long count;

    /**
     * construct a new Statistic with default value.
     */
    public Statistic() { }

    /**
     * construct a new Statistic with given value.
     * 
     * @param sum summations of statistics
     * @param avg average of statistics
     * @param max maximum number in statistics.
     * @param min minimum number in statistics.
     * @param count total count of statistics.
     */
    public Statistic(double sum, double avg, double max, double min, long count) {
        super();
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    /**
     * @return the sum
     */
    public double getSum() {
        return sum;
    }

    /**
     * @param sum the sum to set
     */
    public void setSum(double sum) {
        this.sum = sum;
    }

    /**
     * @return the avg
     */
    public double getAvg() {
        return avg;
    }

    /**
     * @param avg the avg to set
     */
    public void setAvg(double avg) {
        this.avg = avg;
    }

    /**
     * @return the max
     */
    public double getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * @return the min
     */
    public double getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(long count) {
        this.count = count;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Statistic other = (Statistic) obj;
        if (Double.doubleToLongBits(avg) != Double.doubleToLongBits(other.avg))
            return false;
        if (count != other.count)
            return false;
        if (Double.doubleToLongBits(max) != Double.doubleToLongBits(other.max))
            return false;
        if (Double.doubleToLongBits(min) != Double.doubleToLongBits(other.min))
            return false;
        if (Double.doubleToLongBits(sum) != Double.doubleToLongBits(other.sum))
            return false;
        return true;
    }

}
