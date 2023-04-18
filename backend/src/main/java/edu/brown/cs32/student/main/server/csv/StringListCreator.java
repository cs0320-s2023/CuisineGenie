package edu.brown.cs32.student.main.server.csv;

import java.util.List;

public class StringListCreator implements CreatorFromRow<List<String>> {

  @Override
  public List<String> create(List<String> row) throws FactoryFailureException {
    if (row == null) {
      throw new FactoryFailureException("create received a null row", null);
    } else {
      return row;
    }
  }
}
