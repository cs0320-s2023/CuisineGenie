package edu.brown.cs32.student.main.HandlerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs32.student.main.server.Server;
import edu.brown.cs32.student.main.server.handlers.LoadHandler;
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
import javax.swing.plaf.nimbus.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.Assert;

public class TestViewHandler {

  @BeforeAll
  public static void setup_before_everything() {

    // Set the Spark port number. This can only be done once, and has to
    // happen before any route maps are added. Hence using @BeforeClass.
    // Setting port 0 will cause Spark to use an arbitrary available port.
    Spark.port(0);
    // Don't try to remember it. Spark won't actually give Spark.port() back
    // until route mapping has started. Just get the port number later. We're using
    // a random _free_ port to remove the chances that something is already using a
    // specific port on the system used for testing.

    // Remove the logging spam during tests
    //   This is surprisingly difficult. (Notes to self omitted to avoid complicating things.)

    // SLF4J doesn't let us change the logging level directly (which makes sense,
    //   given that different logging frameworks have different level labels etc.)
    // Changing the JDK *ROOT* logger's level (not global) will block messages
    //   (assuming using JDK, not Log4J)
    Logger.getLogger("").setLevel(Level.WARNING); // empty name = root logger
  }

  /**
   * Shared state for all tests. We need to be able to mutate it (adding recipes etc.) but never
   * need to replace the reference itself. We clear this state out after every test runs.
   */


  @BeforeEach
  public void setup() {
    //Server server = new Server();
    List<List<String>> csv = new ArrayList<>();
    Spark.get("load", new LoadHandler(csv));
    Spark.get("view", new ViewHandler(csv));
    Spark.init();
    Spark.awaitInitialization();
  }

  @AfterEach
  public void teardown() {

    // Gracefully stop Spark listening on both endpoints
    Spark.unmap("/load");
    Spark.unmap("/view");


    //Spark.unmap("/weather");
    Spark.awaitStop(); // don't proceed until the server is stopped
  }

  /**
   * Helper to start a connection to a specific API endpoint/params
   *
   * @param apiCall the call string, including endpoint (NOTE: this would be better if it had more
   *                structure!)
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
    //clientConnection.setRequestMethod("GET");

    clientConnection.connect();
    return clientConnection;
  }
  @Test
  public void viewNoFileTest() throws IOException {
    HttpURLConnection clientConnection = tryRequest("view");
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("error_csv_not_loaded", resp.get("result"));
    clientConnection.disconnect();
  }
  @Test
  public void viewEmptyFileTest() throws IOException {
    HttpURLConnection clientConnection = tryRequest("load?filepath=empty.csv");
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("empty.csv", resp.get("filepath"));
    clientConnection = tryRequest("view");
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi2 = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("Please load a csv to view, using /loadcsv", resp2.get("errorMessage"));
    clientConnection.disconnect();
  }

  @Test
  public void viewTenStars() throws IOException {
    HttpURLConnection clientConnection = tryRequest("load?filepath=ten-stars.csv");
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("ten-stars.csv", resp.get("filepath"));
    clientConnection = tryRequest("view");
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi2 = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals(11, ((List) resp2.get("data")).size());
    clientConnection.disconnect();
  }

  @Test
  public void extraParamsTest() throws IOException {
    HttpURLConnection clientConnection = tryRequest("load?filepath=ten-stars.csv");
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("ten-stars.csv", resp.get("filepath"));
    clientConnection = tryRequest("view?fakeparam=thisIsFake");
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi2 = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("error_bad_request", resp2.get("result"));
    assertEquals("view endpoint requires no params", resp2.get("errorMessage"));
    clientConnection.disconnect();
  }

  @Test
  public void largerCSVSizeTest() throws IOException {
    HttpURLConnection clientConnection = tryRequest("load?filepath=larger.csv");
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("larger.csv", resp.get("filepath"));
    clientConnection = tryRequest("view");
    assertEquals(200, clientConnection.getResponseCode());
    Moshi moshi2 = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
        Types.newParameterizedType(Map.class, String.class, Object.class));
    Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals(6, ((List) resp2.get("data")).size());
    clientConnection.disconnect();
  }

}