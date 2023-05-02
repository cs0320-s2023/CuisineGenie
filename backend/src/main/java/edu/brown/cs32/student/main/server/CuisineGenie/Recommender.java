package edu.brown.cs32.student.main.server.CuisineGenie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.lang.reflect.Type;
import java.util.HashMap;

import spark.Request;
import spark.Response;
import spark.Route;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

public class Recommender implements Route 
{

    private List<Responses.RecipeID> mealIDs; // this will be given to us by the frontend
    private RecipeUtils recipeUtils;
    private List<String> categoriesList;
    private List<String> areasList;

    public Recommender() {
        this.recipeUtils = new RecipeUtils();
        this.categoriesList = new ArrayList<String>();
        this.areasList = new ArrayList<String>();
    }

    public void getTopCategories() throws IOException {
        // List<Responses.RecipeID> recommendedList = new ArrayList<>();
        // List<String> categoriesList = new ArrayList<String>();

        try {
            for(Responses.RecipeID meal : mealIDs) {
                String category = this.recipeUtils.getCategory(meal);
    
                if(this.categoriesList.contains(category) == false) {
                    this.categoriesList.add(category);
                }
    
                // for(String ingredient : ingredientsList) {
                //     String ingredientURL = "https://www.themealdb.com/api/json/v1/1/filter.php?i=" + ingredient;
    
                    // confused about what this would return??
                    // an ingredient can have more than one meal
                    // List<Responses.RecipeID> mealsList = this.recipeUtils.callAPI(ingredientURL, Responses.RecipeID.class);
                    // Random rand = new Random();
                    // int randomIndex = rand.nextInt(mealsList.size());
                    // Responses.RecipeID recommendedMeal = mealsList.get(randomIndex);
                    // recommendedList.add(recommendedMeal);
            }
        }
        catch(IOException e) {
            System.err.print("Error"); // change this later
        }

    }

    public void getTopAreas() throws IOException {
        try{
            for(Responses.RecipeID meal : mealIDs) {
                String area = this.recipeUtils.getArea(meal);

                if(this.areasList.contains(area) == false) {
                    this.areasList.add(area);
                }
            }
        }
        catch(IOException e) {
            System.err.print("Error"); // change this later
        }
    }

    public String mostCommonCategory() {
        String mostCommon = "";
        for(int i = 0; i < this.categoriesList.size(); i++) {
            for(int j = 1; j < this.categoriesList.size() - 1; j++) {
                if(this.categoriesList.get(i) == this.categoriesList.get(j)) {
                    mostCommon = this.categoriesList.get(i);
                }
            }
        }

        // nothing in common
        if(mostCommon == "") {
            Random r = new Random();
            int randomItem = r.nextInt(categoriesList.size());
            mostCommon = categoriesList.get(randomItem);
        }
        
        return mostCommon;
    }

    public List<Responses.RecipeID> filterByCategory() throws IOException {
        String url = "https://themealdb.com/api/json/v1/1/filter.php?c=" + this.mostCommonCategory();
        List<Responses.RecipeID> filteredByCategory = this.recipeUtils.callAPI(url, Responses.RecipeID.class);

        return filteredByCategory;
    }

    public List<Responses.RecipeID> filterByArea() throws IOException {
        List<Responses.RecipeID> recommendedList = new ArrayList<>();

        List<Responses.RecipeID> filteredByCategory = this.filterByCategory();

        for(Responses.RecipeID meal : filteredByCategory) {
            String url = "https://themealdb.com/api/json/v1/1/lookup.php?i=" + meal;
            List<Responses.RecipeID> singleMeal = this.recipeUtils.callAPI(url, Responses.RecipeID.class);

            for(String area : this.areasList) {
                if(area == this.recipeUtils.getArea(meal)) {
                    recommendedList.add(singleMeal.get(0)); // it will only have one item in it anyways
                }
            }
        }

        return recommendedList;
    }

    // public List<Responses.RecipeID> handleRecommendation() {
    //     this.getTopCategories();
    //     this.getTopAreas();
    //     this.filterByArea();
    // }

    // public void dislike() {

    // }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        if(request.queryParams().size() != 0) {
            return serialize(viewFailureResponse("error_bad_request", "no endpoint was found"));
        }

        this.getTopCategories();
        this.getTopAreas();
        List<Responses.RecipeID> recommendedList = this.filterByArea();
        
        return serialize(viewSuccessResponse(recommendedList));
    }

    private Map<String, Object> viewSuccessResponse(List<Responses.RecipeID> ids) {
        Map<String, Object> responses = new HashMap<>();
        responses.put("result", "success");
        responses.put("ids", ids); // for frontend to fetch
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
