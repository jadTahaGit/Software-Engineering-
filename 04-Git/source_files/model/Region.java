package swt.model;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a region that is a composition of cities connected by streets.
 *
 * <p>This class is immutable.
 */
public class Region {
  private final Set<City> cities;
  private final Set<Street> streets;

  public Region(Set<City> cities, Set<Street> streets) {
    this.cities = Set.copyOf(cities);
    this.streets = Set.copyOf(streets);
  }

  public Set<City> getCities() {
    return Set.copyOf(cities);
  }

  public Set<Street> getStreets() {
    return Set.copyOf(streets);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Region)) {
      return false;
    }
    Region region = (Region) o;
    return Objects.equals(cities, region.cities) && Objects.equals(streets, region.streets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cities, streets);
  }

  @Override
  public String toString() {
    return "Region{" + "cities=" + cities + ", streets=" + streets + '}';
  }
}
