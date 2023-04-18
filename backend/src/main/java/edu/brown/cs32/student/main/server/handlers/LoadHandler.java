package edu.brown.cs32.student.main.server.handlers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs32.student.main.server.csv.CSVParser;
import edu.brown.cs32.student.main.server.csv.FactoryFailureException;
import edu.brown.cs32.student.main.server.csv.StringListCreator;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handler class for the /loadcsv API endpoint.
 */
public class LoadHandler implements Route {

  private final List<List<String>> csv;
  public String filepath;

  String folderFilepath = "/Users/jennifertran/Desktop/sprint-4-jtran43-kmukai/backend/src/main/java/edu/brown/cs32/student/main/server/handlers/data/";

  /**
   * Constructor accepts some shared state
   *
   * @param csv the List storing the CSV file contents for the entire server
   */
  public LoadHandler(List<List<String>> csv) {
    this.csv = csv;
  }

  /**
   * Handles requests to the /loadcsv endpoint by loading the given filepath's
   * csv contents into the global csv state variable.
   *
   * @param request  the request to handle
   * @param response use to modify properties of the response
   * @return response content
   * @throws Exception This is part of the interface; we don't have to throw
   *                   anything.
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    this.filepath = request.queryParams("filepath");

    if (request.queryParams().size() != 1 || filepath == null) {
      return serialize(loadFailureResponse("error_bad_request",
          "Please input a filepath. Current supported filepaths: " + HandlerErrorHelper.currFilepaths()));
    }

    try {
      FileReader reader = new FileReader(folderFilepath + filepath);
      CSVParser<List<String>> parser = new CSVParser<>(reader, new StringListCreator(),
          false);
      this.csv.clear();
      this.csv.addAll(parser.parse());
      return serialize(loadSuccessResponse("success", this.filepath));
    } catch (IOException e) {
      return serialize(loadFailureResponse("error_datasource",
          "Please supply a valid filepath. Current supported filepaths: " + HandlerErrorHelper.currFilepaths()));
    } catch (FactoryFailureException e) {
      return serialize(loadFailureResponse("error_datasource",
          "Factory failure encountered; a row in the CSV was null (internal issue)"));
    }
  }

  /**
   * Returns a Map containing a success response to be converted to JSON.
   * 
   * @return a Map<String,Object> containing response fields
   */
  public Map<String, Object> loadSuccessResponse(String responseType, String filepath) {
    Map<String, Object> responses = new HashMap<>();
    responses.put("result", responseType);
    responses.put("filepath", filepath);

    return responses;
  }

  /**
   * Returns a Map containing a failure response to be converted to JSON.
   * 
   * @return a Map<String,Object> containing response fields
   */
  public Map<String, Object> loadFailureResponse(String responseType, String errorMessage) {
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