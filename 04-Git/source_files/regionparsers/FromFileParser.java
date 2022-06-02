package swt.regionparsers;

import com.google.common.base.Splitter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import swt.model.City;
import swt.model.Region;
import swt.model.Street;

/**
 * Implements RegionParser by loading regions from a file.
 *
 * <p>The files are expected to have the following format: cities: city-names streets: from to
 * length
 */
public class FromFileParser implements RegionParser {
  private final String fileName;

  private Set<String> cityNames;
  private Map<String, List<String>> cityNeighbors;
  private Set<StreetDescription> streetDescriptions;
  private boolean expectCityEntries;
  private boolean expectStreetEntries;

  public FromFileParser(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public Region parseRegion() throws RegionParsingException {
    cityNeighbors = new HashMap<>();
    cityNames = new HashSet<>();
    streetDescriptions = new HashSet<>();
    expectCityEntries = false;
    expectStreetEntries = false;
    try {
      return readRegionFromFile();
    } catch (IOException e) {
      throw new RegionParsingException(e);
    }
  }

  private Region readRegionFromFile() throws IOException, RegionParsingException {
    try (BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
      String line;
      while ((line = br.readLine()) != null) {
        parseInputLine(line);
      }

      Map<String, City> citiesByName = new HashMap<>();
      for (String cityName : cityNames) {
        instantiateCity(cityName, citiesByName);
      }

      Set<Street> streets = new HashSet<>();
      for (StreetDescription description : streetDescriptions) {
        streets.add(
            new Street(
                citiesByName.get(description.fromCity),
                citiesByName.get(description.toCity),
                description.distance));
      }

      return new Region(new HashSet<>(citiesByName.values()), streets);
    }
  }

  private void instantiateCity(String cityName, Map<String, City> citiesByName) {
    if (citiesByName.containsKey(cityName)) {
      return;
    }

    Set<City> neighbors = new HashSet<>();
    for (String neighborName : cityNeighbors.get(cityName)) {
      instantiateCity(neighborName, citiesByName);
      neighbors.add(citiesByName.get(neighborName));
    }

    City newCity = new City(cityName, neighbors);
    citiesByName.put(cityName, newCity);
  }

  private void parseInputLine(String line) throws RegionParsingException {
    if (line.startsWith("cities")) {
      expectCityEntries = true;
      expectStreetEntries = false;
    } else if (line.startsWith("streets")) {
      expectCityEntries = false;
      expectStreetEntries = true;
    } else if (expectCityEntries) {
      String cityName = parseCityName(line);
      cityNames.add(cityName);
      cityNeighbors.put(cityName, new ArrayList<>());
    } else if (expectStreetEntries) {
      StreetDescription street = parseStreetDescription(line);
      streetDescriptions.add(street);
    }

    throw new RegionParsingException("Unexpected line: " + line);
  }

  private String parseCityName(String description) {
    return description.trim();
  }

  private StreetDescription parseStreetDescription(String description) {
    List<String> split = Splitter.on(' ').splitToList(description.trim());
    String from = split.get(0);
    String to = split.get(1);
    int streetLength = Integer.parseInt(split.get(2));

    cityNeighbors.get(from).add(to);
    cityNeighbors.get(to).add(from);

    return new StreetDescription(from, to, streetLength);
  }

  private static class StreetDescription {
    private final String fromCity;
    private final String toCity;
    private final int distance;

    private StreetDescription(String fromCity, String toCity, int distance) {
      this.fromCity = fromCity;
      this.toCity = toCity;
      this.distance = distance;
    }
  }
}
