package edu.brown.cs32.student.main.CSVtests;

import edu.brown.cs32.student.main.CSVtests.ParseClasses.HashSetCreator;
import edu.brown.cs32.student.main.CSVtests.ParseClasses.Person;
import edu.brown.cs32.student.main.CSVtests.ParseClasses.PersonRecordCreator;
import edu.brown.cs32.student.main.server.csv.CSVParser;
import edu.brown.cs32.student.main.server.csv.FactoryFailureException;
import edu.brown.cs32.student.main.server.csv.StringListCreator;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * A test suite for CSV parsing, including the CSVParser class and CreatorFromRow implementations.
 */
public class TestParse {

  // GENERAL TESTS (FileReader, StringListCreator used)

  /** Tests parsing an empty CSV. */
  @Test
  public void TestEmptyCSV() throws IOException, FactoryFailureException {
    CSVParser<List<String>> parser =
        new CSVParser<>(
            new FileReader("src/test/java/edu/brown/cs32/student/main/data/empty.csv"),
            new StringListCreator(),
            false);
    Assertions.assertEquals(new LinkedList<>(), parser.parse());
  }

  /** Tests parsing a 3x3 CSV made up of alphabetical characters. */
  @Test
  public void Test3x3CSVLetters() throws IOException, FactoryFailureException {
    CSVParser<List<String>> parser =
        new CSVParser<>(
            new FileReader("src/test/java/edu/brown/cs32/student/main/data/3x3.csv"),
            new StringListCreator(),
            false);
    Assertions.assertEquals(
        List.of(List.of("a", "b", "c"), List.of("d", "e", "f"), List.of("g", "h", "i")),
        parser.parse());
  }

  /** Tests parsing a 3x3 CSV with a header. */
  @Test
  public void Test3x3CSVLettersHeader() throws IOException, FactoryFailureException {
    CSVParser<List<String>> parser =
        new CSVParser<>(
            new FileReader("src/test/java/edu/brown/cs32/student/main/data/3x3header.csv"),
            new StringListCreator(),
            true);
    Assertions.assertEquals(
        List.of(List.of("a", "b", "c"), List.of("d", "e", "f"), List.of("g", "h", "i")),
        parser.parse());
  }

  /** Tests parsing a 3x3 CSV with numbers in the header. */
  @Test
  public void Test3x3CSVLettersNumberHeader() throws IOException, FactoryFailureException {
    CSVParser<List<String>> parser =
        new CSVParser<>(
            new FileReader("src/test/java/edu/brown/cs32/student/main/data/3x3numHeader.csv"),
            new StringListCreator(),
            true);
    Assertions.assertEquals(
        List.of(List.of("a", "b", "c"), List.of("d", "e", "f"), List.of("g", "h", "i")),
        parser.parse());
  }

  /**
   * Tests parsing a large, asymmetrical CSV that's missing some values and includes capital letters
   * and repeated values.
   */
  @Test
  public void TestLargeMissingValsCSV() throws IOException, FactoryFailureException {
    CSVParser<List<String>> parser =
        new CSVParser<>(
            new FileReader("src/test/java/edu/brown/cs32/student/main/data/large.csv"),
            new StringListCreator(),
            false);
    Assertions.assertEquals(
        List.of(
            List.of(
                "apple", "banana", "amogus", "sus", "35", "six", "ape", "bean", "fix", "howard"),
            List.of(
                "free", "idea", "one", "help", "bye", "tree", "secluded", "andrew", "heel", "pelt"),
            List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            List.of("hello", "", "", "", "05", "send", "preeled", "angrude", "hongrade", "linkage"),
            List.of(
                "america", "america", "america", "america", "america", "america", "america",
                "america", "america", "america"),
            List.of("OOK", "OOK", "OOK", "OOK", "OOK", "OOK", "OOK", "OOK", "OOK", "OOK")),
        parser.parse());
  }

  /** Tests parsing the large CSV with a header added. */
  @Test
  public void TestLargeMissingValsCSVHeader() throws IOException, FactoryFailureException {
    CSVParser<List<String>> parser =
        new CSVParser<>(
            new FileReader("src/test/java/edu/brown/cs32/student/main/data/largeheader.csv"),
            new StringListCreator(),
            true);
    Assertions.assertEquals(
        List.of(
            List.of(
                "apple", "banana", "amogus", "sus", "35", "six", "ape", "bean", "fix", "howard"),
            List.of(
                "free", "idea", "one", "help", "bye", "tree", "secluded", "andrew", "heel", "pelt"),
            List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            List.of("hello", "", "", "", "05", "send", "preeled", "angrude", "hongrade", "linkage"),
            List.of(
                "america", "america", "america", "america", "america", "america", "america",
                "america", "america", "america"),
            List.of("OOK", "OOK", "OOK", "OOK", "OOK", "OOK", "OOK", "OOK", "OOK", "OOK")),
        parser.parse());
  }

  // VARIED READER TESTS (StringListCreator)

  /** Tests parsing the large CSV with a StringReader. */
  @Test
  public void TestLargeStringReader() throws IOException, FactoryFailureException {
    CSVParser<List<String>> parser =
        new CSVParser<>(
            new StringReader(
                "apple, banana, amogus, sus, 35, six, ape, bean, fix, howard\n"
                    + "free, idea, one, help, bye, tree, secluded, andrew, heel, pelt\n"
                    + "1, 2, 3, 4, 5, 6, 7, 8, 9, 0\n"
                    + "hello, , , , 05, send, preeled, angrude, hongrade, linkage\n"
                    + "america, america, america, america, america, america, america, america, america, america\n"
                    + "OOK,OOK,OOK,OOK,OOK,OOK,OOK,OOK,OOK,OOK"),
            new StringListCreator(),
            false);
    Assertions.assertEquals(
        List.of(
            List.of(
                "apple", "banana", "amogus", "sus", "35", "six", "ape", "bean", "fix", "howard"),
            List.of(
                "free", "idea", "one", "help", "bye", "tree", "secluded", "andrew", "heel", "pelt"),
            List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            List.of("hello", "", "", "", "05", "send", "preeled", "angrude", "hongrade", "linkage"),
            List.of(
                "america", "america", "america", "america", "america", "america", "america",
                "america", "america", "america"),
            List.of("OOK", "OOK", "OOK", "OOK", "OOK", "OOK", "OOK", "OOK", "OOK", "OOK")),
        parser.parse());
  }

  /** Tests parsing the 3x3 CSV with a StringReader. */
  @Test
  public void Test3x3StringReader() throws IOException, FactoryFailureException {
    CSVParser<List<String>> parser =
        new CSVParser<>(
            new StringReader("a, b, c\n" + "d, e, f\n" + "g, h, i"),
            new StringListCreator(),
            false);
    Assertions.assertEquals(
        List.of(List.of("a", "b", "c"), List.of("d", "e", "f"), List.of("g", "h", "i")),
        parser.parse());
  }

  // VARIED CREATOR TESTS (FileReader)

  /** Tests for when PersonRecordCreator throws a FactoryFailureException. */
  @Test
  public void TestPersonRecord3x3() throws IOException {
    CSVParser<Person> parser =
        new CSVParser<>(
            new FileReader("src/test/java/edu/brown/cs32/student/main/data/3x3.csv"),
            new PersonRecordCreator(),
            false);
    Assertions.assertThrows(FactoryFailureException.class, parser::parse);
  }

  /** Tests for parsing with the PersonRecordCreator on a CSV of the correct (strict) format. */
  @Test
  public void TestPersonRecordCorrect() throws IOException, FactoryFailureException {
    CSVParser<Person> parser =
        new CSVParser<>(
            new FileReader("src/test/java/edu/brown/cs32/student/main/data/people.csv"),
            new PersonRecordCreator(),
            false);
    Assertions.assertEquals(
        List.of(
            new Person("Billy", "nihilistic", 3),
            new Person("Joe", "hopeful", 83),
            new Person("Colin", "depressed", 16)),
        parser.parse());
  }

  /** Tests parsing with the PersonRecordCreator and a headered CSV. */
  @Test
  public void TestPersonRecordCorrectHeader() throws IOException, FactoryFailureException {
    CSVParser<Person> parser =
        new CSVParser<>(
            new FileReader("src/test/java/edu/brown/cs32/student/main/data/peopleheader.csv"),
            new PersonRecordCreator(),
            true);
    Assertions.assertEquals(
        List.of(
            new Person("Billy", "nihilistic", 3),
            new Person("Joe", "hopeful", 83),
            new Person("Colin", "depressed", 16)),
        parser.parse());
  }

  /** Tests parsing the 3x3 CSV with the HashSetCreator. */
  @Test
  public void TestHashSetCreator3x3() throws IOException, FactoryFailureException {
    CSVParser<HashSet<String>> parser =
        new CSVParser<>(
            new FileReader("src/test/java/edu/brown/cs32/student/main/data/3x3.csv"),
            new HashSetCreator(),
            false);
    Assertions.assertEquals(
        List.of(Set.of("a", "b", "c"), Set.of("d", "e", "f"), Set.of("g", "h", "i")),
        parser.parse());
  }

  /** Tests parsing the 3x3 headered CSV with the HashSetCreator. */
  @Test
  public void TestHashSetCreator3x3Header() throws IOException, FactoryFailureException {
    CSVParser<HashSet<String>> parser =
        new CSVParser<>(
            new FileReader("src/test/java/edu/brown/cs32/student/main/data/3x3header.csv"),
            new HashSetCreator(),
            true);
    Assertions.assertEquals(
        List.of(Set.of("a", "b", "c"), Set.of("d", "e", "f"), Set.of("g", "h", "i")),
        parser.parse());
  }

  /** Tests parsing the large CSV with the HashSetCreator. */
  @Test
  public void TestHashSetCreatorLarge() throws IOException, FactoryFailureException {
    CSVParser<HashSet<String>> parser =
        new CSVParser<>(
            new FileReader("src/test/java/edu/brown/cs32/student/main/data/large.csv"),
            new HashSetCreator(),
            false);
    Assertions.assertEquals(
        List.of(
            Set.of("apple", "banana", "amogus", "sus", "35", "six", "ape", "bean", "fix", "howard"),
            Set.of(
                "free", "idea", "one", "help", "bye", "tree", "secluded", "andrew", "heel", "pelt"),
            Set.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            Set.of("hello", "", "05", "send", "preeled", "angrude", "hongrade", "linkage"),
            Set.of("america"),
            Set.of("OOK")),
        parser.parse());
  }
}