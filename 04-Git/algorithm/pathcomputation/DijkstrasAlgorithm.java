package swt.pathcomputation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import swt.model.City;
import swt.model.Region;
import swt.pathcomputation.Util.RoutingEntry;

/** Implements shortest path computation in a Region with Dijkstra's shortest paths algorithm. */
public class DijkstrasAlgorithm implements ShortestPathAlgorithm {

  @Override
  public List<City> computeShortestPathBetweenCities(City start, City target, Region region) {
    Map<City, List<RoutingEntry>> neighborDistances = Util.computeNeighborDistances(region);
    Map<City, City> predecessorsOnShortestPath =
        computePredecessorsOnShortestPath(start, neighborDistances);
    return extractPathFromPredecessorMap(predecessorsOnShortestPath, target);
  }

  private List<City> extractPathFromPredecessorMap(Map<City, City> predecessors, City target) {
    List<City> extractedPath = new ArrayList<>();
    City currentCity = target;
    extractedPath.add(currentCity);
    // The root in the predecessors map has itself as predecessor.
    while (!currentCity.equals(predecessors.get(currentCity))) {
      currentCity = predecessors.get(currentCity);
      extractedPath.add(currentCity);
    }
    Collections.reverse(extractedPath);

    return extractedPath;
  }

  private Map<City, City> computePredecessorsOnShortestPath(
      City start, Map<City, List<RoutingEntry>> adjacencyLists) {
    Map<City, Integer> distances = Util.getInfiniteDistanceMap(adjacencyLists.keySet());
    distances.put(start, 0);

    Map<City, City> predecessors = new HashMap<>();
    predecessors.put(start, start);

    Queue<RoutingEntry> minDistanceCities =
        new PriorityQueue<>(adjacencyLists.size(), Comparator.comparingInt(r -> r.distance));
    minDistanceCities.add(new RoutingEntry(start, 0));
    Set<City> visitedCities = new HashSet<>();

    // Dijkstra's algorithm
    while (!minDistanceCities.isEmpty()) {
      City current = minDistanceCities.remove().city;
      if (visitedCities.contains(current)) {
        continue;
      }
      visitedCities.add(current);
      for (RoutingEntry neighbor : adjacencyLists.get(current)) {
        int fromCurrentToNeighbor = distances.get(current) + neighbor.distance;
        int shortestDistanceToNeighbor = distances.get(neighbor.city);

        if (fromCurrentToNeighbor < shortestDistanceToNeighbor) {
          distances.put(neighbor.city, fromCurrentToNeighbor);
          predecessors.put(neighbor.city, current);
          minDistanceCities.add(new RoutingEntry(neighbor.city, fromCurrentToNeighbor));
        }
      }
    }

    return predecessors;
  }
}
