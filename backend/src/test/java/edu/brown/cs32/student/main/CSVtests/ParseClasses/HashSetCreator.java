package edu.brown.cs32.student.main.CSVtests.ParseClasses;


    import edu.brown.cs32.student.main.server.csv.CreatorFromRow;
    import edu.brown.cs32.student.main.server.csv.FactoryFailureException;
    import java.util.HashSet;
    import java.util.List;

public class HashSetCreator implements CreatorFromRow<HashSet<String>> {
  public HashSetCreator() {}

  @Override
  public HashSet<String> create(List<String> row) throws FactoryFailureException {
    if (row == null) {
      throw new FactoryFailureException("create received a null row", null);
    } else {
      return new HashSet<>(row);
    }
  }
}
