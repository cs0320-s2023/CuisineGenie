package edu.brown.cs32.student.main.server.weather;

import edu.brown.cs32.student.main.server.weather.WeatherUtil.Coordinates;
import edu.brown.cs32.student.main.server.weather.WeatherUtil.WeatherRecord;
import edu.brown.cs32.student.main.server.weather.Responses.GridResponse;
import edu.brown.cs32.student.main.server.weather.Responses.ForecastResponse;
import edu.brown.cs32.student.main.server.weather.Responses.Forecast;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.lang.reflect.Type;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Capable of requesting weather information from the NWS API.
 */
public class NWSRequester {

    private final String weatherURL = "https://api.weather.gov/points/";
    protected final Coordinates coordinate;

    /**
     * Stores the coordinate pair (lat and lon) to make future requests with
     * 
     * @param coordinate
     */
    public NWSRequester(Coordinates coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Makes a GET request to the NWS API using stored coordinates
     * 
     * credit: https://github.com/cs0320-s2023/sprint-3-abenjell-hmasamur
     * 
     * @return a WeatherRecord, containing information from the successful
     *         request
     * @throws IOException if the request is unsuccessful for any reason
     */
    public WeatherRecord makeRequest() throws IOException {
        String latitude = String.valueOf(Math.round(coordinate.getLatitude() * 100) / 100.0);
        String longitude = String.valueOf(Math.round(coordinate.getLongitude() * 100) / 100.0);

        String gridRequestUrl = weatherURL + latitude + "," + longitude;

        GridResponse gridResponse = this.requestAPI(gridRequestUrl, GridResponse.class);
        String gridResponseUrl = gridResponse.forecastURL().url();

        String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new java.util.Date());

        ForecastResponse forecastResponse = this.requestAPI(gridResponseUrl, ForecastResponse.class);

        Forecast forecast = forecastResponse.forecastPeriods().forecasts().get(0);
        return new WeatherRecord(forecast.temp(), forecast.unit(), timestamp);
    }

    /**
     * Performs the actual get request, writing the JSON response into an
     * Object of type T.
     * 
     *
     * @param <T>       an object storing the response
     * @param urlStr    the url to GET request to
     * @param classType the class type of T
     * @return a T, written into by moshi using the JSON response
     * @throws IOException if the request fails
     */
    public <T> T requestAPI(String urlStr, Type classType) throws IOException {
        URL url;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            throw new IOException(e);
        }
        HttpURLConnection clientConnection = (HttpURLConnection) url.openConnection();

        clientConnection.connect();
        int status = clientConnection.getResponseCode();

        // credit: https://github.com/cs0320-s2023/sprint-3-abenjell-hmasamur
        switch (status) {
            case 404 -> throw new IOException("The entered coordinates were not valid. Please try again.");
            case 500 -> throw new IOException("An unexpected error occurred while connecting to NWS server.");
        }

        InputStreamReader responseReader = new InputStreamReader(clientConnection.getInputStream());
        BufferedReader br = new BufferedReader(responseReader);
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }
        br.close();

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<T> adapter = moshi.adapter(classType);
        return adapter.fromJson(stringBuilder.toString());
    }

}
