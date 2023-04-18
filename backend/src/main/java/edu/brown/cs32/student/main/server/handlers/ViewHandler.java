package edu.brown.cs32.student.main.server.handlers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handler class for the viewcsv API endpoint.
 *
 */
public class ViewHandler implements Route {

    private final List<List<String>> csv;

    /**
     * Constructor accepts shared csv contents for server
     * 
     * @param csv the List storing the CSV file contents for the entire server
     */
    public ViewHandler(List<List<String>> csv) {
        this.csv = csv;
    }

    /**
     * Given a request to view the currently stored csv, returns a response
     * containing the csv as a JSON 2D array.
     *
     * @param request  the request to handle
     * @param response use to modify properties of the response
     * @return response content
     * @throws Exception This is part of the interface; we don't have to throw
     *                   anything.
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        if (request.queryParams().size() != 0) {
            return serialize(viewFailureResponse("error_bad_request", "view endpoint requires no params"));
        }
        if (csv.size() == 0 || csv.size() == 1 && csv.get(0).size() == 0) {
            return serialize(viewFailureResponse("error_csv_not_loaded", "Please load a valid/non-empty csv to view, using /loadcsv"));
        }
        return serialize(viewSuccessResponse());
    }

    /**
     * Returns a Map containing a success response to be converted to JSON.
     * 
     * @return a Map<String,Object> containing response fields
     */
    private Map<String, Object> viewSuccessResponse() {
        Map<String, Object> responses = new HashMap<>();
        responses.put("result", "success");
        responses.put("data", this.csv);
        return responses;
    }

    /**
     * Returns a Map containing a failure response to be converted to JSON.
     * 
     * @return a Map<String,Object> containing response fields
     */
    private Map<String, Object> viewFailureResponse(String responseType, String errorMessage) {
        Map<String, Object> responses = new HashMap<>();
        responses.put("result", responseType);
        responses.put("errorMessage", errorMessage);
        return responses;
    }

    /**
     * Serializes the given Map<String, Object> into JSON to be returned to a
     * requesting user.
     * 
     * @return string representation of the Map in JSON format
     */
    public static String serialize(Map<String, Object> response) {
        // we are converting map to json
        Moshi moshi = new Moshi.Builder().build(); // idk what build means but it starts something
        // this is specifying what the adapter should expect for serializing
        Type mapOfJSONResponse = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapOfJSONResponse);
        return adapter.toJson(response);
    }

}
