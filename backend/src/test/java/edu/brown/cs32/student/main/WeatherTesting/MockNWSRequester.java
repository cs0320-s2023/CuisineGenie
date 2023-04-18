package edu.brown.cs32.student.main.WeatherTesting;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs32.student.main.server.weather.NWSRequester;
import edu.brown.cs32.student.main.server.weather.Responses;
import edu.brown.cs32.student.main.server.weather.Responses.Forecast;
import edu.brown.cs32.student.main.server.weather.WeatherUtil.Coordinates;
import edu.brown.cs32.student.main.server.weather.WeatherUtil.WeatherRecord;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * A child class of WeatherUtils, used solely for testing.
 * This class overrides the getForecast method to result our mock NYC data.
 */
public class MockNWSRequester extends NWSRequester {

  public MockNWSRequester(Coordinates coordinate){
    super(coordinate);
  }
  /**
   * Gets the forecast (WeatherResponse) for our mock NYC data.
   * @return - a WeatherResponse object for the Mock NYC data.
   * @throws IOException
   */
  @Override
  public WeatherRecord makeRequest() throws IOException {
    String mockJson;
    if(this.coordinate.getLatitude() > 40.0) {
      mockJson = MockedData.mockProvidenceForecast;
    } else {
      mockJson = MockedData.mockNewYorkCityForecast;
    }
    String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new java.util.Date());
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Responses.ForecastResponse> adapter = moshi.adapter(Responses.ForecastResponse.class);
    Responses.ForecastResponse forecastResponse = adapter.fromJson(mockJson);
    Forecast forecast = forecastResponse.forecastPeriods().forecasts().get(0);
    return new WeatherRecord(forecast.temp(), forecast.unit(), timestamp);
  }
}
