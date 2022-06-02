package swt.model;

import java.util.Objects;

/**
 * Represents a (directed) street with a length (any unit) between two cities.
 *
 * <p>This class is immutable.
 */
public class Street {
  private final City from;
  private final City to;
  private final int length;

  /**
   * Constructs an instance of this class.
   *
   * @param from Starting city that this street connects
   * @param to Target city that this street connects
   * @param length Lenght of this street
   */
  public Street(City from, City to, int length) {
    this.from = from;
    this.to = to;
    this.length = length;
  }

  public City getFrom() {
    return from;
  }

  public City getTo() {
    return to;
  }

  public int getLength() {
    return length;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Street)) {
      return false;
    }
    Street street = (Street) o;
    return Objects.equals(from, street.from)
        && Objects.equals(to, street.to)
        && length == street.length;
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to, length);
  }

  @Override
  public String toString() {
    return from + " -" + length + "-> " + to;
  }
}
