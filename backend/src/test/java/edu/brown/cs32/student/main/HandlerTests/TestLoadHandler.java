package edu.brown.cs32.student.main.HandlerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import edu.brown.cs32.student.main.server.csv.FactoryFailureException;
import edu.brown.cs32.student.main.server.handlers.LoadHandler;
import edu.brown.cs32.student.main.server.handlers.SearchHandler;
import edu.brown.cs32.student.main.server.handlers.ViewHandler;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.util.Map;
import okio.Buffer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;


public class TestLoadHandler {
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
    Spark.get("load", new LoadHandler(csv));
    Spark.get("search", new SearchHandler(csv));
    Spark.get("view", new ViewHandler(csv));
    Spark.init();
    Spark.awaitInitialization();
  }

  @AfterEach
  public void teardown() {
    // Gracefully stop Spark listening on both endpoints
    Spark.unmap("/load");
    Spark.unmap("/view");
    Spark.unmap("/search");
    // Spark.unmap("/weather");
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

  /**
   * Test if there is an error message when CSV parameter not given
   * @throws IOException
   */

  @Test
  // Recall that the "throws IOException" doesn't signify anything but
  // acknowledgement to the type checker
  public void testIncorrectArgsLoaded() throws IOException {
    HttpURLConnection clientConnection = tryRequest("load");
    // Get an OK response (the *connection* worked, the *API* provides an error
    // response)
    assertEquals(200, clientConnection.getResponseCode());
    // Now we need to see whether we've got the expected Json response.
    // SoupAPIUtilities handles ingredient lists, but that's not what we've got
    // here.
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));

    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("error_bad_request", resp.get("result"));
    assertEquals("Please input a filepath. Current supported filepaths: [empty.csv, stars.csv, ten-stars.csv]",
        resp.get("errorMessage"));


    clientConnection.disconnect();
  }

  /**
   * Tests if there is an error when a fake CSV is loaded
   * @throws IOException
   */
  @Test
  public void testLoadFakeFile() throws IOException {
    HttpURLConnection clientConnection = tryRequest("load?filepath=Thisisfake.csv");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));

    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("error_datasource", resp.get("result"));
    assertEquals("Please supply a valid filepath. Current supported filepaths: [empty.csv, stars.csv, ten-stars.csv]",
        resp.get("errorMessage"));

    clientConnection.disconnect();
  }

  /**
   * Tests if CSV is loaded
   * @throws IOException
   * @throws FactoryFailureException
   */
  @Test
  public void basicTest() throws IOException, FactoryFailureException {
    HttpURLConnection clientConnection = tryRequest("load?filepath=ten-stars.csv");

    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("ten-stars.csv", resp.get("filepath"));
    assertEquals("success", resp.get("result"));



    clientConnection.disconnect();
  }

  /**
   * Tests if error is given when more than 1 arg is given
   * @throws IOException
   */
  @Test
  public void incorrectArgs() throws IOException {
    HttpURLConnection clientConnection = tryRequest("load?filepath=Thisisfake.csv&notAParam=fake");

    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));

    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("error_bad_request", resp.get("result"));

    clientConnection.disconnect();
  }

  /**
   * Testing if recent file is loaded after multiple loads
   * @throws IOException
   */
  @Test
  public void testDoubleLoad() throws IOException {
    HttpURLConnection clientConnection = tryRequest("load?filepath=ten-stars.csv");
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("ten-stars.csv", resp.get("filepath"));


    clientConnection = tryRequest("load?filepath=stars.csv");
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi2 = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("stars.csv", resp2.get("filepath"));
    clientConnection.disconnect();
  }

  /**
   * Tests if empty CSV is loaded. Should be able to load, but throw an error at search
   * @throws IOException
   * @throws FactoryFailureException
   */
  @Test
  public void emptyCSV() throws IOException, FactoryFailureException {
    HttpURLConnection clientConnection = tryRequest("load?filepath=empty.csv");
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("success", resp.get("result"));
    clientConnection.disconnect();
  }

  /**
   * Check if our own CSV for testing is loaded
   * @throws IOException
   * @throws FactoryFailureException
   */

  @Test
  public void basicLargerCSVTest() throws IOException, FactoryFailureException {
    HttpURLConnection clientConnection = tryRequest("load?filepath=larger.csv");

    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("larger.csv", resp.get("filepath"));

    clientConnection.disconnect();
  }

}
