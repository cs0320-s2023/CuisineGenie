package edu.brown.cs32.student.main.server.CuisineGenie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import okio.Buffer;
import spark.Spark;

public class TestBackend() {

    @BeforeAll
    public static void setup_before_everything() {
        Spark.port(0);
        Logger.getLogger("").setLevel(Level.WARNING); // empty name = root logger
    }

    @BeforeEach
    public void setup() {
        // Server server = new Server();
        List<List<String>> csv = new ArrayList<>();
        Spark.get("generaterecipes", new Generator());
        Spark.init();
        Spark.awaitInitialization();
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

    // Success Responses
    @Test
    public void AllSameCategory() {
        // beef
        HttpURLConnection clientConnection = tryRequest("generaterecipe?1=52874&2=52878&3=53071&4=52997&5=52904");
        assertEquals(200, clientConnection.getResponseCode());

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map response = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[]", response.get("ids").toString()); // fill in

        // clientConnection = tryRequest("searchcsv?target=Sol&hasHeader=true&colID=ProperName");
        // assertEquals(200, clientConnection.getResponseCode());

        // Moshi moshi2 = new Moshi.Builder().build();
        // JsonAdapter<Map<String, Object>> adapter2 = moshi2.adapter(
        //         Types.newParameterizedType(Map.class, String.class, Object.class));
        // Map resp2 = adapter2.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        // assertEquals("[0.0]", resp2.get("rowIndices").toString());
        
        clientConnection.disconnect();
    }

    @Test
    public void AllDifferentCategories() {
        // beef, chicken, vegetarian, dessert, breakfast
        HttpURLConnection clientConnection = tryRequest("generaterecipe?1=52874&2=53050&3=52807&4=53049&5=52965");
        assertEquals(200, clientConnection.getResponseCode());

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map response = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[]", response.get("ids").toString()); // fill in
    }

    @Test
    public void OneCommonCategory() {
        // chicken, chicken, vegetarian, dessert, breakfast
        HttpURLConnection clientConnection = tryRequest("generaterecipe?1=52765&2=53050&3=52807&4=53049&5=52965");
        assertEquals(200, clientConnection.getResponseCode());

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map response = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[]", response.get("ids").toString()); // fill in
    }

    @Test
    public void TieCategories() {
        // chicken, chicken, vegetarian, vegetarian, breakfast
        HttpURLConnection clientConnection = tryRequest("generaterecipe?1=52765&2=53050&3=52807&4=52771&5=52965");
        assertEquals(200, clientConnection.getResponseCode());

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(
                Types.newParameterizedType(Map.class, String.class, Object.class));
        Map response = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
        assertEquals("[]", response.get("ids").toString()); // fill in
    }

    @Test
    public void AllSameAreas() {

    }

    @Test
    public void DifferentAreas() {

    }

    // Failure Responses
}

