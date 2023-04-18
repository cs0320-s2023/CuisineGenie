package edu.brown.cs32.student.main.server.csv;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Stores the necessary data for searching a CSV file. Prints all search results
 * to standard out.
 */
public class CSVSearch {

  CSVParser<List<String>> parser;
  List<List<String>> csv;
  List<List<String>> csvMaybeHeader;
  List<String> header;

  /**
   * Creates a CSVParser and attempts to parse the given CSV using it. If
   * successful, stores the CSV
   * data for future search operations.
   *
   * @param filename  the filepath of the chosen CSV file from content root
   * @param hasHeader true if the top row of the CSV file is column labels, false
   *                  otherwise
   * @throws IOException             if file I/O errors are encountered with the
   *                                 given filename
   * @throws FactoryFailureException if a null (not empty) row is encountered
   *                                 while parsing
   */
  public CSVSearch(String filename, boolean hasHeader) throws IOException, FactoryFailureException {
    // set up a CSVParser, parse the CSV
    this.parser = new CSVParser<>(new FileReader(filename), new StringListCreator(), false);
    this.csv = parser.parse();
    if (this.csv.size() == 0) {
      this.csv.add(List.of()); // avoid accessing empty inner list
    }
    this.header = new LinkedList<>();
    if (hasHeader) {
      header = csv.get(0);
      csv.remove(csv.get(0));
    }
    this.csvMaybeHeader = csv;
  }

  public CSVSearch(List<List<String>> csv, boolean hasHeader) {
    this.csv = csv;
    if (this.csv.size() == 0) {
      this.csv.add(List.of()); // avoid accessing empty inner list
    }
    header = csv.get(0);
    if (hasHeader) {
      this.csvMaybeHeader = new ArrayList<>(csv);
      this.csvMaybeHeader.remove(csvMaybeHeader.get(0));
    } else {
      this.csvMaybeHeader = csv;
    }
  }

  /**
   * Returns whether a list of strings at the given index in the local CSV
   * contains the target
   * string.
   *
   * @param rowIndex the row to search
   * @param target   the string to search for
   * @return true if target is contained in the list at rowIndex, false otherwise
   */
  private boolean containedInCSVRow(int rowIndex, String target) {
    return csvMaybeHeader.get(rowIndex).contains(target);
  }

  /**
   * Prints output to the user regarding the given list of ints (rows that the
   * target value was
   * found on)
   *
   * @param rows a list of ints
   */
  // private void printRows(List<Integer> rows) {
  // if (!rows.isEmpty()) {
  // System.out.format("Rows containing target value: %s\n", rows);
  // } else {
  // System.out.println("No rows contained the target value.");
  // }
  // }

  /**
   * Searches row-wise for the target string in the CSV.
   *
   * @param target the string search target
   * @return the list of row indices where the target was found
   */
  public List<Integer> search(String target) {
    List<Integer> rows = new LinkedList<>();
    for (int i = 0; i < csvMaybeHeader.size(); i++) {
      if (containedInCSVRow(i, target)) {
        rows.add(i);
      }
    }
    return rows;
  }

  /**
   * Searches along the given column index in the CSV for the target value.
   *
   * @param target   the string target value
   * @param colIndex an int index of the column in which to search
   * @return a list of row indices where the target value is in the column with
   *         the given index
   */
  public List<Integer> search(String target, int colIndex) {
    List<Integer> rows = new LinkedList<>();
    if (csvMaybeHeader.size() > 0) {
      if (colIndex >= 0 && colIndex < csvMaybeHeader.get(0).size()) {
        for (int i = 0; i < csvMaybeHeader.size(); i++) {
          if (csvMaybeHeader.get(i).get(colIndex).equals(target)) {
            rows.add(i);
          }
        }
      }
    }
    return rows;
  }

  /**
   * Finds the column index associated with the given column label if it exists in
   * the header, then
   * searches that column for the target value.
   *
   * @param target   the string target value
   * @param colLabel a string column label in the header of this CSVSearch's CSV
   *                 file, to be
   *                 searched
   * @return a list of int row indices where the target value is in the labelled
   *         column
   */
  public List<Integer> search(String target, String colLabel) {
    // find colIndex using colLabel
    int colIndex = header.indexOf(colLabel);
    return search(target, colIndex);
  }
}
