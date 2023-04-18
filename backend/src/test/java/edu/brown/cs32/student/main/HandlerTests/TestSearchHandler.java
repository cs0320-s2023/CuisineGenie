package edu.brown.cs32.student.main.HandlerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs32.student.main.server.handlers.LoadHandler;
import edu.brown.cs32.student.main.server.handlers.SearchHandler;
import edu.brown.cs32.student.main.server.handlers.ViewHandler;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

public class TestSearchHandler {
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
        // This is surprisingly difficult. (Notes to self omitted to avoid complicating
        // things.)

        // SLF4J doesn't let us change the logging level directly (which makes sense,
        // given that different logging frameworks have different level labels etc.)
        // Changing the JDK *ROOT* logger's level (not global) will block messages
        // (assuming using JDK, not Log4J)
        Logger.getLogger("").setLevel(Level.WARNING); // empty name = root logger
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
     * Tests if correct row index is returned
     * 
     * @throws IOException
     */

    @Test
    public void searchTenStars() throws IOException {
        HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=ten-stars.csv");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("ten-stars.csv", resp.get("filepath"));
        clientConnection = tryRequest("searchcsv?target=Sol&hasHeader=true&colID=ProperName");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi2 = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[0.0]", resp2.get("rowIndices").toString());
        clientConnection.disconnect();
    }

    /**
     * tests if full row is given
     * 
     * @throws IOException
     */

    @Test
    public void searchTenStarsRowReturn() throws IOException {
        HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=ten-stars.csv");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("ten-stars.csv", resp.get("filepath"));
        clientConnection = tryRequest("searchcsv?target=Sol&hasHeader=true&colID=ProperName");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi2 = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[[0, Sol, 0, 0, 0]]", resp2.get("data").toString());
        clientConnection.disconnect();
    }

    /**
     * Tests if error is given when empty csv is searched
     * 
     * @throws IOException
     */
    @Test
    public void searchEmptyCSV() throws IOException {
        HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=empty.csv");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("empty.csv", resp.get("filepath"));
        clientConnection = tryRequest("searchcsv?target=Sol&hasHeader=true&colID=ProperName");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi2 = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("error_datasource", resp2.get("result").toString());
        assertEquals("Please load a valid CSV using the /loadcsv endpoint", resp2.get("errorMessage"));

        clientConnection.disconnect();
    }

    /**
     * Tests if empty rows are returned when non-existent target is given
     * 
     * @throws IOException
     */
    @Test
    public void searchNonExistent() throws IOException {
        HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=ten-stars.csv");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("ten-stars.csv", resp.get("filepath"));
        clientConnection = tryRequest("searchcsv?target=iLoveCS&hasHeader=true&colID=2");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi2 = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[]", resp2.get("data").toString());
        assertEquals("[]", resp2.get("rowIndices").toString());
        clientConnection.disconnect();
    }

    /**
     * Tests if correct row is given with Col Label is given
     * 
     * @throws IOException
     */
    @Test
    public void searchWithColLabel() throws IOException {
        HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=ten-stars.csv");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("ten-stars.csv", resp.get("filepath"));
        clientConnection = tryRequest("searchcsv?target=RigelKentaurusB&hasHeader=false&colID=ProperName");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi2 = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[[71454, RigelKentaurusB, -0.50359, -0.42128, -1.1767]]", resp2.get("data").toString());
        clientConnection.disconnect();
    }

    /**
     * Tests if search with Col Index is given
     * 
     * @throws IOException
     */
    @Test
    public void searchWithColIndex() throws IOException {
        HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=ten-stars.csv");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("ten-stars.csv", resp.get("filepath"));
        clientConnection = tryRequest("searchcsv?target=RigelKentaurusB&hasHeader=false&colID=1");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi2 = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[[71454, RigelKentaurusB, -0.50359, -0.42128, -1.1767]]", resp2.get("data").toString());
        clientConnection.disconnect();
    }

    /**
     * Tests if error is thrown when Col index and label are given
     * 
     * @throws IOException
     */
    @Test
    public void searchWithExtraParams() throws IOException {
        HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=ten-stars.csv");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("ten-stars.csv", resp.get("filepath"));
        clientConnection = tryRequest(
                "searchcsv?target=RigelKentaurusB&hasHeader=false&colIndex=1&colLabel=ProperName");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi2 = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("Cannot supply both colIndex and colLabel params", resp2.get("errorMessage").toString());
        clientConnection.disconnect();
    }

    /**
     * Tests if empty row is given when non existent column id is given
     * 
     * @throws IOException
     */
    @Test
    public void searchWithFakeCol() throws IOException {
        HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=ten-stars.csv");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("ten-stars.csv", resp.get("filepath"));
        clientConnection = tryRequest("searchcsv?target=RigelKentaurusB&hasHeader=false&colID=Fake");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi2 = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[]", resp2.get("data").toString());
        clientConnection.disconnect();
    }

    /**
     * Tests if correct row index is given on larger.csv
     * 
     * @throws IOException
     */
    @Test
    public void searchLargerCSV() throws IOException {
        HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=larger.csv");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("larger.csv", resp.get("filepath"));
        clientConnection = tryRequest("searchcsv?target=idea&hasHeader=false&colID=1");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi2 = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[1.0]", resp2.get("rowIndices").toString());
        clientConnection.disconnect();
    }

    /**
     * Tests if correct row is given on larger.csv
     * 
     * @throws IOException
     */

    @Test
    public void searchLargerCSVRowReturn() throws IOException {
        HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=larger.csv");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("larger.csv", resp.get("filepath"));
        clientConnection = tryRequest("searchcsv?target=idea&hasHeader=false&colID=1");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi2 = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[[free, idea, one, help, bye, tree, secluded, andrew, heel, pelt]]",
                resp2.get("data").toString());
        clientConnection.disconnect();
    }

    /**
     * Tests if correct empty row is given on larger.csv when non existent target is
     * given
     * 
     * @throws IOException
     */

    @Test
    public void searchLargerNonExistent() throws IOException {
        HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=larger.csv");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("larger.csv", resp.get("filepath"));
        clientConnection = tryRequest("searchcsv?target=TimNelson&hasHeader=false&colID=nimothytelson");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi2 = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[]", resp2.get("data").toString());
        clientConnection.disconnect();
    }

    /**
     * Tests if correct row index is given on largerheader.csv with header param =
     * true
     * 
     * @throws IOException
     */

    @Test
    public void searchLargerHeaderCSV() throws IOException {
        HttpURLConnection clientConnection = tryRequest("loadcsv?filepath=largerheader.csv");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("largerheader.csv", resp.get("filepath"));
        clientConnection = tryRequest("searchcsv?target=idea&hasHeader=true&colID=name");
        assertEquals(200, clientConnection.getResponseCode());
        Moshi moshi2 = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[1.0]", resp2.get("rowIndices").toString());
        clientConnection.disconnect();
    }

}
