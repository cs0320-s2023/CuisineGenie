package edu.brown.cs32.student.main.server.csv;

import java.util.List;

public interface CreatorFromRow<T> {
  T create(List<String> row) throws FactoryFailureException;
}
