package edu.brown.cs32.student.main.server.handlers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs32.student.main.server.csv.CSVSearch;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handler class for the /searchcsv API endpoint.
 */
public class SearchHandler implements Route {

  private final List<List<String>> csv;
  private boolean hasHeader = false;

  /**
   * Constructor accepts some shared state
   * 
   * @param csv the List storing the CSV file contents for the entire server
   */
  public SearchHandler(List<List<String>> csv) {
    this.csv = csv;
  }

  /**
   * Handles requests to the /searchcsv endpoint by returning a JSON response
   * including search results from the given query.
   *
   * @param request  the request to handle
   * @param response use to modify properties of the response
   * @return response content
   * @throws Exception This is part of the interface; we don't have to throw
   *                   anything.
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    if (this.csv.isEmpty()) {
      return serialize(searchFailureResponse("error_datasource",
          "Please load a valid CSV using the /loadcsv endpoint"));
    } else if (this.csv.get(0).isEmpty()) {
      return serialize(
          searchFailureResponse("error_datasource", "Please load a valid CSV using the /loadcsv endpoint"));

    }
    String target = request.queryParams("target");
    String colIndexStr = request.queryParams("colIndex");
    String colLabel = request.queryParams("colLabel");
    String colID = request.queryParams("colID");
    String hasHeaderStr = request.queryParams("hasHeader");
    hasHeader = Boolean.parseBoolean(hasHeaderStr);
    // List<String> params = new ArrayList<>(List.of(target, colIndexStr, colLabel,
    // hasHeaderStr));
    Map<String, String> params = new HashMap<>();

    //have col param both strings, first convert to int, check if not null. if  null then 
    //it is a word. if not null then number.

    CSVSearch searcher = new CSVSearch(csv, hasHeader);
    List<Integer> results = null;

    if (target == null || hasHeaderStr == null) {
      return serialize(searchFailureResponse("error_bad_request",
          "One of query params <target> (string) or <hasHeader> (boolean) was not supplied"));
    }
    if (colID == null){
      return serialize(searchFailureResponse("error_bad_request", "Please input a column identifier (column index or label)"));
    }
   else{
      int colIDNum;
      try {
        colIDNum = Integer.parseInt(colID);
        results = searcher.search(target, colIDNum);
      } catch (NumberFormatException e) {
        results = searcher.search(target, colID);
      }
      //new addition ends here
    }
    params.put("target", target);
    params.put("hasHeader", hasHeaderStr);
    return serialize(searchSuccessResponse(results, params, hasHeaderStr));

  }

  /**
   * Returns a Map containing a success response to be converted to JSON.
   * 
   * @return a Map<String,Object> containing response fields
   */
  private Map<String, Object> searchSuccessResponse(List<Integer> results, Map<String, String> params, String hasHeaderStr) {
    Map<String, Object> responses = new HashMap<>();
    responses.put("parameters", params);
    responses.put("rowIndices", results);
    responses.put("hasHeader", hasHeaderStr);
    responses.put("result", "success");
    List<List<String>> rows = new ArrayList<>();
    for (int i : results) {
      if (hasHeader) {
        i++;
      }
      rows.add(csv.get(i));
    }
    responses.put("data", rows);
    return responses;
  }

  /**
   * Returns a Map containing a failure response to be converted to JSON.
   * 
   * @return a Map<String,Object> containing response fields
   */
  private Map<String, Object> searchFailureResponse(String responseType, String errorMessage) {
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
    Moshi moshi = new Moshi.Builder().build();
    Type mapOfJSONResponse = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapOfJSONResponse);
    return adapter.toJson(response);
  }

}