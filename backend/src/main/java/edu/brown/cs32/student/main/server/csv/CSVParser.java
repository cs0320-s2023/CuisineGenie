package edu.brown.cs32.student.main.server.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * A generic parser class for comma-separated variable (CSV) data.
 *
 * @param <T> the type to parse each row into
 */
public class CSVParser<T> {

  private final BufferedReader bReader;
  private final CreatorFromRow<T> creator;
  private final boolean hasHeader;

  /**
   * A constructor for the CSVParser class. Stores the given reader in a BufferedReader for the
   * parse() function to use.
   *
   * @param reader any Reader, ready to read from the desired CSV file
   * @param creator a CreatorFromRow of the same type T as this CSVParser class
   * @param hasHeader true only if the first row of the CSV is a header containing column labels
   */
  public CSVParser(Reader reader, CreatorFromRow<T> creator, boolean hasHeader) {
    this.bReader = new BufferedReader(reader);
    this.creator = creator;
    this.hasHeader = hasHeader;
  }

  /**
   * Reads the CSV file through this class's given reader and parses it line-wise into a list of T,
   * where each T was created with this class's given creator.
   *
   * @return a list of type T, where each list element was created using the given creator
   * @throws IOException if I/O issues are encountered with the given reader
   * @throws FactoryFailureException if the given creator encounters issues
   */
  public List<T> parse() throws IOException, FactoryFailureException {
    List<T> lst = new ArrayList<>();
    boolean skippedFirstLine = false;
    while (bReader.ready()) {
      String line = bReader.readLine();
      if (line == null) {
        break; // to avoid issues at the end of StringReader strings that don't terminate in EOF
      }
      // skip this line if it is a header
      if (!skippedFirstLine && hasHeader) {
        skippedFirstLine = true;
        continue;
      }
      // parse and add to list
      List<String> parsedLine = List.of(line.replaceAll(" ", "").split(","));
      T res = creator.create(parsedLine);
      lst.add(res);
    }
    return lst;
  }
}
