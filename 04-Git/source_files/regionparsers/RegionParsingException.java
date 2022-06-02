package swt.regionparsers;

/** Exception that is thrown when parsing a region fails. */
public class RegionParsingException extends Exception {
  public RegionParsingException(Throwable cause) {
    super(cause);
  }

  public RegionParsingException(String message) {
    super(message);
  }
}
