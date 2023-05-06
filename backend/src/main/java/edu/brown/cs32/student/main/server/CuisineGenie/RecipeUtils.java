package edu.brown.cs32.student.main.server.CuisineGenie;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import edu.brown.cs32.student.main.server.CuisineGenie.Responses.MealProperties;
import edu.brown.cs32.student.main.server.CuisineGenie.Responses.Meals;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains helper methods to access Meals and MealProperties data
 */
public class RecipeUtils {

    /**
     * returns the category of a given meal
     * @param meals -- Meal object
     * @return the category of the meal
     */
    public String getCategory(Meals meals) {
        String category = meals.mealProperties().get(0).category();
        return category;
    }

    /**
     * returns the area of a given meal
     * Public to be used in other classes
     * @param meals -- Meal object
     * @return the area of the meal
     */
    public String getArea(Meals meals) {

        String area = meals.mealProperties().get(0).area();
        return area;

    }

    /**
     * Returns the mealID for a Meal
     * Public to be used in other classes
     * @param meals -- meal object
     * @return Meal ID
     */
    public String getID(Meals meals)  {
        String id = meals.mealProperties().get(0).mealID();
        return id;
    }


    /**
     * This method makes a call to the MealDB API
     * Public to be used in other classes
     * credit to: sprint-3-abenjell-hmasamur
     * @param url -- MealDB API based on what endpoint we are using
     * @param endpoint -- specific endpoint for the MealDB API
     * @param classType -- class type that the API results are converted to
     * @param <T> -- Converted type class
     * @return List of the objects
     * @throws IOException -- exception thrown when the API cannot be called
     */
    public <T> T callAPI(String url, String endpoint, Type classType) throws IOException {
        String apiCall = this.getRecipeEndpointURL(endpoint);
        String urlAPI = url + apiCall;
        URL urlEndpoint = new URL(urlAPI);
        HttpURLConnection clientConnection = (HttpURLConnection) urlEndpoint.openConnection();

        clientConnection.connect();
        int status = clientConnection.getResponseCode();

        switch (status) {
            case 404 -> throw new IOException("The entered coordinates were not valid. Please try again.");
            case 500 -> throw new IOException("An unexpected error occurred while connecting to NWS server.");
        }

        InputStreamReader resultReader = new InputStreamReader(clientConnection.getInputStream());
        String jsonAsString = this.readerToString(resultReader);

        return this.fromJson(classType, jsonAsString);
    }

    /**
     * Returns the recipe ID for the API call
     * Public to be used in other classes
     * @param recipeID -- mealID as a string
     * @return -- mealID
     */

    public String getRecipeEndpointURL(String recipeID) {
        return recipeID;
    }

    /**
     * Helper method for reading API results
     * public for testing/used in other classes
     * @param reader
     * @return String (lines read)
     * @throws IOException
     */

    public String readerToString(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }
        br.close();

        return stringBuilder.toString();
    }

    /**
     * Helper method that converts String to Type
     * Public to be used in other classes
     * @param classType -- type to be converted to
     * @param jsonString -- string that is converted
     * @param <T>
     * @return List of strings converted to a type
     * @throws IOException
     */

    public <T> T fromJson(Type classType, String jsonString) throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<T> adapter = moshi.adapter(classType);
        return adapter.fromJson(jsonString);
    }
}
