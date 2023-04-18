package edu.brown.cs32.student.main.FuzzTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.brown.cs32.student.main.server.handlers.LoadHandler;
import edu.brown.cs32.student.main.server.handlers.SearchHandler;
import edu.brown.cs32.student.main.server.handlers.ViewHandler;
import edu.brown.cs32.student.main.server.handlers.WeatherHandler;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

public class FuzzTests {
  final static int NUM_TRIALS = 1000;

  @BeforeAll
  public static void setup_before_everything() {

    Spark.port(0);
    Logger.getLogger("").setLevel(Level.WARNING);
  }

  /**
   * Shared state for all tests. We need to be able to mutate it (adding recipes
   * etc.) but never need to replace
   * the reference itself. We clear this state out after every test runs.
   */
  @BeforeEach
  public void setup() {
    // Server server = new Server();
    List<List<String>> csv = new ArrayList<>();
    Spark.get("loadcsv", new LoadHandler(csv));
    Spark.get("searchcsv", new SearchHandler(csv));
    Spark.get("viewcsv", new ViewHandler(csv));
    Spark.get("weather", new WeatherHandler(0.5));
    Spark.init();
    Spark.awaitInitialization();
  }

  @AfterEach
  public void teardown() {
    // Gracefully stop Spark listening on both endpoints
    Spark.unmap("/loadcsv");
    Spark.unmap("/viewcsv");
    Spark.unmap("/searchcsv");
    Spark.unmap("/weather");
    Spark.awaitStop(); // don't proceed until the server is stopped
  }

  /**
   * Helper to start a connection to a specific API endpoint/params
   *
   * @param apiCall the call string, including endpoint
   *                (NOTE: this would be better if it had more structure!)
   * @return the connection for the given URL, just after connecting
   * @throws IOException if the connection fails for some reason
   */
  static private HttpURLConnection tryRequest(String apiCall) throws IOException {
    // Configure the connection (but don't actually send the request yet)
    URL requestURL = new URL("http://localhost:" + Spark.port() + "/" + apiCall);
    System.out.println(requestURL);
    HttpURLConnection clientConnection = (HttpURLConnection) requestURL.openConnection();

    // The default method is "GET", which is what we're using here.
    // If we were using "POST", we'd need to say so.
    // clientConnection.setRequestMethod("GET");

    clientConnection.connect();
    return clientConnection;
  }

  @Test
  public void fuzzTestURL() throws IOException {
    for (int i=0; i<NUM_TRIALS; i++ ) {
      String input = TestingHelpers.getRandomStringBounded(10);
      HttpURLConnection clientConnection = tryRequest(input);
      int status = clientConnection.getResponseCode();
      if (status == 500) {
        fail("invalid status" + status);
        assertTrue(false);
      }
      // Fuzz testing -- just expect no exceptions, termination, ...
      clientConnection.connect();
    }
  }

  @Test
  public void fuzzTestSearch() throws IOException {
    HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=ten-stars.csv");
    assertEquals(200, clientConnection.getResponseCode());
    for (int i=0; i<NUM_TRIALS; i++ ) {
      double input = TestingHelpers.getRandomNumBounded(10);
      clientConnection = tryRequest("search?target=Sol&hasHeader=true&colIndex=" + input);
      int status = clientConnection.getResponseCode();
      if (status == 500) {
        fail("invalid status" + status);
        assertTrue(false);
      }
      // Fuzz testing -- just expect no exceptions, termination, ...
      clientConnection.connect();
    }
  }

  @Test
  public void fuzzTestSearchString() throws IOException {
    HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=ten-stars.csv");
    assertEquals(200, clientConnection.getResponseCode());
    for (int i=0; i<NUM_TRIALS; i++ ) {
      String input = TestingHelpers.getRandomStringBounded(10);
      clientConnection = tryRequest("search?hasHeader=true&colIndex=&target=" + input);
      int status = clientConnection.getResponseCode();
      if (status == 500) {
        fail("invalid status" + status);
        assertTrue(false);
      }
      // Fuzz testing -- just expect no exceptions, termination, ...
      clientConnection.connect();
    }
  }

  }







