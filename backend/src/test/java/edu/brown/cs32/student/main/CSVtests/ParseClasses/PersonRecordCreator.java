package edu.brown.cs32.student.main.CSVtests.ParseClasses;


    import edu.brown.cs32.student.main.server.csv.CreatorFromRow;
    import edu.brown.cs32.student.main.server.csv.FactoryFailureException;
    import java.util.List;

public class PersonRecordCreator implements CreatorFromRow<Person> {
  public PersonRecordCreator() {}

  @Override
  public Person create(List<String> row) throws FactoryFailureException {
    if (row.size() == 3) {
      try {
        return new Person(row.get(0), row.get(1), Integer.parseInt(row.get(2)));
      } catch (NumberFormatException e) {
        throw new FactoryFailureException("Third argument in following row was not an int: ", row);
      }
    } else {
      throw new FactoryFailureException(
          "Row size incorrect in following row (match Person record): ", row);
    }
  }
}
