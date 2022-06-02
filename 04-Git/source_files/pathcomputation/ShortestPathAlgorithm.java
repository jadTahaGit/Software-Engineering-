package swt.pathcomputation;

import java.util.List;
import swt.model.City;
import swt.model.Region;

/** Interface for classes that can compute the shortest path between two cities in a region. */
public interface ShortestPathAlgorithm {

  /**
   * Computes the shortest path between (and including) two given cities in a region.
   *
   * <p>The length of a path is the sum of street lengths on that path. The two cities are assumed
   * to be present in the region.
   *
   * @param start city where the path starts
   * @param target city where the path ends
   * @param region region of the cities
   * @return a list of cities that represents the shortest path (including start and end)
   */
  List<City> computeShortestPathBetweenCities(City start, City target, Region region);
}
