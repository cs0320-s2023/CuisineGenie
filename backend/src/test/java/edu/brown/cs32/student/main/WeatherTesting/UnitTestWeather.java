package edu.brown.cs32.student.main.WeatherTesting;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.brown.cs32.student.main.server.weather.WeatherCache;
import edu.brown.cs32.student.main.server.weather.WeatherUtil;
import edu.brown.cs32.student.main.server.weather.WeatherUtil.Coordinates;
import edu.brown.cs32.student.main.server.weather.WeatherUtil.WeatherRecord;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/**
 * A unit test suite for the /weather endpoint's backend functionality.
 */
public class UnitTestWeather {

  @Test
  public void testMockProvidenceRequest() throws IOException {
    MockNWSRequester requester = new MockNWSRequester(new Coordinates(39.0, -70.0, 0.5));
    WeatherRecord res = requester.makeRequest();
    assertEquals(res.temp(), 55);
    assertEquals(res.unit(), "F");
    assertNotNull(res.timestamp());
  }

  @Test
  public void testMockProvidenceRequestCaching() throws IOException {
    Coordinates coords = new Coordinates(39.0, -70.0, 0.5);
    MockNWSRequester requester = new MockNWSRequester(coords);
    WeatherRecord res = requester.makeRequest();
    WeatherCache cache = new WeatherCache(2, TimeUnit.SECONDS);
    assertEquals(res.temp(), 55);
    assertEquals(res.unit(), "F");
    assertNotNull(res.timestamp());
  }
  @Test
  public void testMockNYRequest() throws IOException {
    Coordinates coords = new Coordinates(41.0, -70.0, 0.5);
    MockNWSRequester requester = new MockNWSRequester(coords);
    WeatherRecord res = requester.makeRequest();
    WeatherCache cache = new WeatherCache(2, TimeUnit.SECONDS);
    assertEquals(res.temp(), 51);
    assertEquals(res.unit(), "F");
    assertNotNull(res.timestamp());
  }
  @Test
  public void testMockNYRequestCachingTimer() throws IOException, InterruptedException {
    Coordinates coords = new Coordinates(41.0, -70.0, 0.5);
    MockNWSRequester requester = new MockNWSRequester(coords);
    WeatherRecord res = requester.makeRequest();
    WeatherCache cache = new WeatherCache(1, TimeUnit.SECONDS); // 1 second expire
    assertEquals(res.temp(), 51);
    assertEquals(res.unit(), "F");
    assertNotNull(res.timestamp());
    sleep(1000);
    assertEquals(res.temp(), 51);
    assertEquals(res.unit(), "F");
    assertNotNull(res.timestamp());
  }
}
