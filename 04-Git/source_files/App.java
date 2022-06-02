package swt;

import java.util.List;
import java.util.Objects;
import swt.model.City;
import swt.model.Region;
import swt.pathcomputation.ShortestPathAlgorithm;
import swt.regionparsers.FromFileParser;
import swt.regionparsers.RegionParser;
import swt.regionparsers.RegionParsingException;

/**
 * Main class of the application.
 *
 * <p>This class calls the parser and the algorithm and outputs the result.
 */
public class App {
  private final RegionParser parser;
  private final ShortestPathAlgorithm algorithm;

  App(RegionParser parser, ShortestPathAlgorithm algorithm) {
    this.parser = Objects.requireNonNull(parser);
    this.algorithm = Objects.requireNonNull(algorithm);
  }

  /** Main method. */
  public static void main(String[] args) {
    // TODO No implementation of algorithm exists yet
    App app =
        new App(
            new FromFileParser("input.txt"),
            new ShortestPathAlgorithm() {
              @Override
              public List<City> computeShortestPathBetweenCities(
                  City start, City target, Region region) {
                return List.of();
              }
            });
    app.computeNavigation();
  }

  private void computeNavigation() {
    try {
      Region region = parser.parseRegion();
      City startCity = getCityByName("n0", region);
      City targetCity = getCityByName("n1", region);

      List<City> shortestPath =
          algorithm.computeShortestPathBetweenCities(startCity, targetCity, region);
      System.out.println(shortestPath);
    } catch (RegionParsingException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
  }

  private static City getCityByName(String cityName, Region region) {
    for (City city : region.getCities()) {
      if (cityName.equals(city.getName())) {
        return city;
      }
    }
    return null;
  }
}
