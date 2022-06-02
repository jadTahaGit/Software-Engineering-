package swt.pathcomputation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import swt.model.City;
import swt.model.Region;
import swt.model.Street;

class Util {
  private Util() {}

  static Map<City, List<RoutingEntry>> computeNeighborDistances(Region region) {
    Map<City, List<RoutingEntry>> neighbors = new HashMap<>();
    // First we add all the cities to the map
    for (City city : region.getCities()) {
      neighbors.put(city, new ArrayList<>());
    }

    // Then we add neighbors according to the edges
    for (Street street : region.getStreets()) {
      // Streets are bidirectional, thus we add the entry to both ends of the street.
      neighbors.get(street.getFrom()).add(new RoutingEntry(street.getTo(), street.getLength()));
      neighbors.get(street.getTo()).add(new RoutingEntry(street.getFrom(), street.getLength()));
    }

    return neighbors;
  }

  static <T> Map<T, Integer> getInfiniteDistanceMap(Set<T> nodes) {
    final int infinity = Integer.MAX_VALUE;
    Map<T, Integer> distances = new HashMap<>();
    for (T node : nodes) {
      distances.put(node, infinity);
    }

    return distances;
  }

  static class RoutingEntry {
    City city;
    int distance;

    RoutingEntry(City city, int distance) {
      this.city = city;
      this.distance = distance;
    }
  }
}
