package edu.brown.cs32.student.main.server.handlers;

import com.squareup.moshi.Moshi;
import edu.brown.cs32.student.main.server.weather.WeatherUtil;
import edu.brown.cs32.student.main.server.weather.WeatherCache;
import edu.brown.cs32.student.main.server.weather.WeatherUtil.Coordinates;
import edu.brown.cs32.student.main.server.weather.WeatherUtil.WeatherRecord;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import spark.Request;
import spark.Response;
import spark.Route;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;

/**
 * Handler class for the /weather API endpoint.
 *
 */
public class WeatherHandler implements Route {
    private final Double maxDiff;
    private final WeatherCache cache;

    /**
     * Constructor accepts some shared state
     */
    public WeatherHandler(double maxDiff) {
        this.maxDiff = maxDiff;
        this.cache = new WeatherCache(2, TimeUnit.HOURS);
    }

    /**
     * Given a request supplying lat and lon, either grabs the related weather
     * report out of the cache or queries the NWS API for that information.
     *
     * @param request  the request to handle
     * @param response use to modify properties of the response
     * @return response content
     * @throws Exception This is part of the interface; we don't have to throw
     *                   anything.
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String latitude = request.queryParams("lat");
        String longitude = request.queryParams("lon");
        if (request.queryParams().size() != 2) {
            return serialize(WeatherFailureResponse("error_bad_request", "Please only input lat and lon fields."));
        }
        Double lat;
        Double lon;
        try {
            lat = Double.parseDouble(latitude);
            lon = Double.parseDouble(longitude);
        } catch (NumberFormatException e) {
            return serialize(WeatherFailureResponse("error_bad_request",
                    "lat and lon fields must be correctly formatted as doubles"));
        }

        WeatherUtil.Coordinates coordinates = new Coordinates(lat, lon, this.maxDiff);

        try {
            WeatherRecord res = cache.queryWeather(coordinates);
            return serialize(WeatherSuccessResponse(res, latitude, longitude));
        } catch (UncheckedExecutionException e) {
            return serialize(
                    WeatherFailureResponse("error_bad_request", e.getCause().getMessage()));
        }

    }

    /**
     * Returns a Map containing a failure response to be converted to JSON.
     * 
     * @return a Map<String,Object> containing response fields
     */
    private Map<String, Object> WeatherFailureResponse(String responseType, String errorMessage) {
        Map<String, Object> responses = new HashMap<>();
        responses.put("result", responseType);
        responses.put("errorMessage", errorMessage);

        return responses;
    }

    /**
     * Returns a Map containing a success response to be converted to JSON.
     * 
     * @return a Map<String,Object> containing response fields
     */
    private Map<String, Object> WeatherSuccessResponse(WeatherRecord record, String lat, String lon) {
        Map<String, Object> responses = new HashMap<>();
        responses.put("lat", lat);
        responses.put("lon", lon);
        responses.put("date", record.timestamp());
        responses.put("temperature", record.temp());
        responses.put("unit", record.unit());

        return responses;
    }

    /**
     * Serializes Maps into JSON strings
     * @param response
     * @return
     */

    public String serialize(Map<String, Object> response) {
        // we are converting map to json
        Moshi moshi = new Moshi.Builder().build();
        // this is specifying what the adapter should expect for serializing
        Type mapOfJSONResponse = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapOfJSONResponse);
        return adapter.toJson(response);
    }
}
