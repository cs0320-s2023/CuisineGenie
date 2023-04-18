package edu.brown.cs32.student.main.WeatherTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs32.student.main.server.Server;
import edu.brown.cs32.student.main.server.csv.FactoryFailureException;
import edu.brown.cs32.student.main.server.handlers.LoadHandler;
import edu.brown.cs32.student.main.server.handlers.SearchHandler;
import edu.brown.cs32.student.main.server.handlers.ViewHandler;
import edu.brown.cs32.student.main.server.handlers.WeatherHandler;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import okio.Buffer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.plaf.nimbus.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.Assert;

public class TestWeatherHandler {
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

  //////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////// TESTS ///////////////////////////////
  //////////////////////////////////////////////////////////////////////////////
//
  @Test
  public void testNoArgs() throws IOException {
    HttpURLConnection clientConnection = tryRequest("weather");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("error_bad_request", resp.get("result"));

    clientConnection.disconnect();
  }

  @Test
  public void testOnlyLat() throws IOException {
    HttpURLConnection clientConnection = tryRequest("weather?lat=36.311005");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("error_bad_request", resp.get("result"));

    clientConnection.disconnect();
  }

  @Test
  public void testOnlyLon() throws IOException {
    HttpURLConnection clientConnection = tryRequest("weather?lon=-94.127434");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("error_bad_request", resp.get("result"));

    clientConnection.disconnect();
  }

  @Test
  public void testProvidence() throws IOException {
    HttpURLConnection clientConnection = tryRequest("weather?lat=36.311005&lon=-94.127434");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("36.311005", resp.get("lat"));
    assertEquals("-94.127434", resp.get("lon"));
    assertEquals("F", resp.get("unit"));
    assertNotNull(resp.get("temperature"));
    assertNotNull(resp.get("date"));

    clientConnection.disconnect();
  }

  @Test
  public void testButteMontana() throws IOException {
    HttpURLConnection clientConnection = tryRequest("weather?lat=46.0038&lon=-112.5348");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("46.0038", resp.get("lat"));
    assertEquals("-112.5348", resp.get("lon"));
    assertEquals("F", resp.get("unit"));
    assertNotNull(resp.get("temperature"));
    assertNotNull(resp.get("date"));

    clientConnection.disconnect();
  }
  @Test
  public void testInvalidCoords() throws IOException {
    HttpURLConnection clientConnection = tryRequest("weather?lat=9999&lon=9999");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertNull(resp.get("lat"));
    assertNull(resp.get("lon"));

    clientConnection.disconnect();
  }
  @Test
  public void testProvidenceCaching() throws IOException {
    HttpURLConnection clientConnection = tryRequest("weather?lat=36.311005&lon=-94.127434");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("36.311005", resp.get("lat"));
    assertEquals("-94.127434", resp.get("lon"));
    assertEquals("F", resp.get("unit"));
    assertNotNull(resp.get("temperature"));
    assertNotNull(resp.get("date"));
    Object date1 = resp.get("date");

    HttpURLConnection clientConnection2 = tryRequest("weather?lat=36.311005&lon=-94.127434");
    assertEquals(200, clientConnection2.getResponseCode());


    Map resp2 = adapter.fromJson(new Buffer().readFrom(clientConnection2.getInputStream()));

    assertEquals("36.311005", resp2.get("lat"));
    assertEquals("-94.127434", resp2.get("lon"));
    assertEquals("F", resp2.get("unit"));
    assertNotNull(resp2.get("temperature"));
    assertNotNull(resp2.get("date"));
    Object date2 = resp2.get("date");

    assertEquals(date1, date2); // if cached, date should be equal

    clientConnection.disconnect();
  }

  @Test
  public void testButteMontanaCaching() throws IOException {
    HttpURLConnection clientConnection = tryRequest("weather?lat=46.0038&lon=-112.5348");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("46.0038", resp.get("lat"));
    assertEquals("-112.5348", resp.get("lon"));
    assertEquals("F", resp.get("unit"));
    assertNotNull(resp.get("temperature"));
    assertNotNull(resp.get("date"));
    Object date1 = resp.get("date");

    HttpURLConnection clientConnection2 = tryRequest("weather?lat=46.0038&lon=-112.5348");
    assertEquals(200, clientConnection2.getResponseCode());


    Map resp2 = adapter.fromJson(new Buffer().readFrom(clientConnection2.getInputStream()));

    assertEquals("46.0038", resp2.get("lat"));
    assertEquals("-112.5348", resp2.get("lon"));
    assertEquals("F", resp2.get("unit"));
    assertNotNull(resp2.get("temperature"));
    assertNotNull(resp2.get("date"));
    Object date2 = resp2.get("date");

    assertEquals(date1, date2); // if cached, date should be equal

    clientConnection.disconnect();
  }

  @Test
  public void testNonDoubleInputs() throws IOException {
    HttpURLConnection clientConnection = tryRequest("weather?lat=sauce&lon=juice");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("error_bad_request", resp.get("result"));
    assertEquals("lat and lon fields must be correctly formatted as doubles",
        resp.get("errorMessage"));

    clientConnection.disconnect();
  }
  @Test
  public void testLatNonDouble() throws IOException {
    HttpURLConnection clientConnection = tryRequest("weather?lat=sauce&lon=-100.0");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("error_bad_request", resp.get("result"));
    assertEquals("lat and lon fields must be correctly formatted as doubles",
        resp.get("errorMessage"));

    clientConnection.disconnect();
  }
  @Test
  public void testLonNonDouble() throws IOException {
    HttpURLConnection clientConnection = tryRequest("weather?lat=45&lon=juice");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("error_bad_request", resp.get("result"));
    assertEquals("lat and lon fields must be correctly formatted as doubles",
        resp.get("errorMessage"));

    clientConnection.disconnect();
  }
  @Test
  public void testTooManyInputs() throws IOException {
    HttpURLConnection clientConnection = tryRequest("weather?lat=45&lon=-115&something=???");
    assertEquals(200, clientConnection.getResponseCode());

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));

    assertEquals("error_bad_request", resp.get("result"));
    assertEquals("Please only input lat and lon fields.",
        resp.get("errorMessage"));

    clientConnection.disconnect();
  }

}
