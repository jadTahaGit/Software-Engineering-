package swt.regionparsers;

import swt.model.Region;

/** Interface for classes that can parse a region from some input source. */
public interface RegionParser {
  /**
   * Parse a region from the input source.
   *
   * @return the parsed region
   * @throws RegionParsingException if parsing failed for some reason
   */
  Region parseRegion() throws RegionParsingException;
}
