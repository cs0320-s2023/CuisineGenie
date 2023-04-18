package edu.brown.cs32.student.main.CSVtests;

import edu.brown.cs32.student.main.server.csv.CSVSearch;
import edu.brown.cs32.student.main.server.csv.FactoryFailureException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** A test suite for the CSVSearch user utility. */
public class TestSearch {

    // GENERAL TESTS

    /** Tests searching an empty CSV. */
    @Test
    public void TestEmptyCSV() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/empty.csv", false);
        Assertions.assertEquals(new LinkedList<>(), searcher.search("ANYTHING THERE?"));
    }

    /** Tests searching an empty CSV and requesting a column index. */
    @Test
    public void TestEmptyCSVHeader() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/empty.csv", true);
        Assertions.assertEquals(new LinkedList<>(), searcher.search("ANYTHING THERE?", 0));
    }

    /** Tests searching an empty CSV and requesting a column label. */
    @Test
    public void TestEmptyCSVHeaderLabel() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/empty.csv", true);
        Assertions.assertEquals(new LinkedList<>(), searcher.search("ANYTHING THERE?", "hoopla"));
    }

    /** Tests the general case of searching a small CSV. */
    @Test
    public void Test3x3Simple() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/3x3.csv", false);
        Assertions.assertEquals(List.of(0), searcher.search("a"));
    }

    /** Tests searching for a value not in the list. */
    @Test
    public void Test3x3NotInList() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/3x3.csv", false);
        Assertions.assertEquals(List.of(), searcher.search("z"));
    }

    /** Tests searching for a value that exists in every row. */
    @Test
    public void Test3x3Dupes() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/3x3Copies.csv", false);
        Assertions.assertEquals(List.of(0, 1, 2), searcher.search("b"));
    }

    /**
     * Tests searching for a value in the CSV, but not the specified column index.
     */
    @Test
    public void Test3x3NotInCol() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/3x3header.csv", true);
        Assertions.assertEquals(List.of(), searcher.search("a", 2));
    }

    /**
     * Tests searchign for a value in the CSV, but not in the column with the
     * specified label.
     *
     * @throws IOException
     * @throws FactoryFailureException
     */
    @Test
    public void Test3x3NotInColLabel() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/3x3header.csv", true);
        Assertions.assertEquals(List.of(), searcher.search("a", "third"));
    }

    /**
     * Tests searching for a value in the given label's column.
     *
     * @throws IOException
     * @throws FactoryFailureException
     */
    @Test
    public void Test3x3HeaderString() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/3x3header.csv", true);
        Assertions.assertEquals(List.of(1), searcher.search("f", "third"));
    }

    /**
     * Tests the general case of searching in a larger, asymmetrical CSV.
     *
     * @throws IOException
     * @throws FactoryFailureException
     */
    @Test
    public void TestLargeSimple() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/large.csv", false);
        Assertions.assertEquals(List.of(4), searcher.search("america"));
    }

    /**
     * Tests searching with an empty query.
     *
     * @throws IOException
     * @throws FactoryFailureException
     */
    @Test
    public void TestLargeEmptySearch() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/large.csv", false);
        Assertions.assertEquals(List.of(3), searcher.search(""));
    }

    /**
     * Tests searching for a value in a column by index, without a header.
     *
     * @throws IOException
     * @throws FactoryFailureException
     */
    @Test
    public void TestLargeSimpleIndex() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/largeheader.csv", true);
        Assertions.assertEquals(List.of(1), searcher.search("secluded", 6));
    }

    /** Tests searching for a value in a column by index. */
    @Test
    public void TestLargeSimpleLabel() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/largeheader.csv", true);
        Assertions.assertEquals(List.of(1), searcher.search("secluded", "33"));
    }

    /** Tests searching for a value that exists in every row. */
    @Test
    public void TestLargeDupesSimple() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/largedupes.csv", false);
        Assertions.assertEquals(List.of(0, 1, 2, 3, 4, 5), searcher.search("six"));
    }

    /** Tests searching by index for a value that exists in every row. */
    @Test
    public void TestLargeDupesCol() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/largedupes.csv", false);
        Assertions.assertEquals(List.of(0, 1, 2, 3), searcher.search("six", 5));
    }

    /** Tests searching by label for a value. */
    @Test
    public void TestLargeHeaderSimple() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/largeheader.csv", true);
        Assertions.assertEquals(List.of(0), searcher.search("sus", "Yoshikage"));
    }

    /** Tests searching for a value not in the labelled column. */
    @Test
    public void TestLargeHeaderNotInCol() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/largeheader.csv", true);
        Assertions.assertEquals(List.of(), searcher.search("05", "Yoshikage"));
    }

    /** Tests searching for a value not in the labelled column. */
    @Test
    public void TestLargeHeaderIndexOutOfBounds() throws IOException, FactoryFailureException {
        CSVSearch searcher = new CSVSearch("src/test/java/edu/brown/cs32/student/main/data/largeheader.csv", true);
        Assertions.assertEquals(List.of(), searcher.search("america", 15));
    }
}