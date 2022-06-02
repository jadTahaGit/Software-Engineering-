package swt.model;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a city that has a name and is connected to neighboring cities.
 *
 * <p>This class is immutable.
 */
public class City {
  private final String name;
  private final Set<City> neighbors;

  public City(String name, Set<City> neighbors) {
    this.name = name;
    this.neighbors = Set.copyOf(neighbors);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof City)) {
      return false;
    }
    City city = (City) o;
    return Objects.equals(name, city.name) && Objects.equals(neighbors, city.neighbors);
  }

  public String getName() {
    return name;
  }

  public Set<City> getNeighbors() {
    return Set.copyOf(neighbors);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return name;
  }
}
