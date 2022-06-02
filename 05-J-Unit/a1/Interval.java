import java.util.Arrays;
import java.util.Objects;

public class Interval {

    /**
     * the lower bound of the interval
     */
    protected long low;

    /**
     * the upper bound of the interval
     */
    protected long high;

    /**
     * This method acts as constructor for an interval.
     *
     * @param low
     *            the lower bound
     * @param high
     *            the upper bound
     */
    public Interval(Long low, Long high) {
        if (low == null || high == null) {
            throw new IllegalArgumentException(
                "neither the lower bound nor the upper bound may be null!");
        }
        if (low > high) {
            throw new IllegalArgumentException(
                "the lower bound cannot be greater than the upper bound!");
        }

        this.low = low;
        this.high = high;
    }

    /**
     * This method acts as constructor for a single-value interval.
     *
     * @param value
     *            for the lower and upper bound
     */
    public Interval(Long value) {
        this(value, value);
    }

    /**
     * This method creates a new interval instance representing the union of
     * this interval with another interval.
     *
     * The lower bound and upper bound of the new interval is the minimum of
     * both lower bounds and the maximum of both upper bounds, respectively.
     *
     * @param other
     *            the other interval
     * @return the new interval with the respective bounds
     */
    public Interval union(Interval other) {
        return new Interval(Math.min(low, other.low), Math.max(high, other.high));
    }

    /**
     * This method creates a new interval instance representing the intersection
     * of this interval with another interval.
     *
     * The lower bound and upper bound of the new interval is the maximum of
     * both lower bounds and the minimum of both upper bounds, respectively.
     *
     * @param other
     *            the other interval
     * @return the new interval with the respective bounds
     */
    public Interval intersect(Interval other) {
        if (this.intersects(other)) {
            return new Interval(Math.max(low, other.low), Math.min(high, other.high));
        }
        return null;
    }

    /**
     * This method determines if this interval intersects with another interval.
     *
     * @param other
     *            the other interval
     * @return true if the intervals intersect, else false
     */
    public boolean intersects(Interval other) {
        return (low >= other.low && low <= other.high)
            || (high >= other.low && high <= other.high)
            || (low <= other.low && high >= other.high);
    }

    /**
     * This method determines if this interval contains another interval.
     *
     * The method still returns true, if the borders match. An empty interval
     * does not contain any interval and is not contained in any interval
     * either. So if the callee or parameter is an empty interval, this method
     * will return false.
     *
     * @param other
     *            the other interval
     * @return true if this interval contains the other interval, else false
     */
    public boolean contains(Interval other) {
        return (low <= other.low && other.high <= high);
    }

    /**
     * This method multiplies this interval with another interval.
     *
     * @param other
     *            interval to multiply this interval with
     * @return new interval that represents the multiplication of the two
     *         intervals
     */
    public Interval times(Interval other) {
        long[] values = {
            low * other.low,
            low * other.high,
            high * other.low,
            high * other.high
        };
        return new Interval(
            Arrays.stream(values).min().getAsLong(),
            Arrays.stream(values).max().getAsLong());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof Interval)) {
            return false;
        } else {
            Interval another = (Interval) other;
            return low == another.low && high == another.high;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(low, high);
    }

    @Override
    public String toString() {
        return "[" + low + "; " + high + "]";
    }
}