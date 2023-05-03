package edu.brown.cs32.student.main.server.CuisineGenie;

import edu.brown.cs32.student.main.server.CuisineGenie.Responses.Area;
import edu.brown.cs32.student.main.server.CuisineGenie.Responses.RecipeID;
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

public class Generator implements Route 
{

    private List<Responses.RecipeID> mealIDs; // this will be given to us by the frontend
    private RecipeUtils recipeUtils;
    private List<Responses.Category> categoriesList;
    private List<Responses.Area> areasList;

    public Generator() {
        this.recipeUtils = new RecipeUtils();
        this.categoriesList = new ArrayList<Responses.Category>();
        this.areasList = new ArrayList<Responses.Area>();
    }

    public void getAllCategories() throws IOException {


        try {
            for(Responses.RecipeID meal : this.mealIDs) {
                Responses.Category category = this.recipeUtils.getCategory(meal);
    
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

    public void getAllAreas() throws IOException {
        try{
            for(Responses.RecipeID meal : mealIDs) {
                Responses.Area area = this.recipeUtils.getArea(meal);

                if(this.areasList.contains(area) == false) {
                    this.areasList.add(area);
                }
            }
        }
        catch(IOException e) {
            System.err.print("Error"); // change this later
        }
    }

    public Responses.Category mostCommonCategory() {

        // nothing in common
        Random r = new Random();
        int randomItem = r.nextInt(categoriesList.size());
        Responses.Category  mostCommon = categoriesList.get(randomItem);

        for(int i = 0; i < this.categoriesList.size(); i++) {
            for(int j = 1; j < this.categoriesList.size() - 1; j++) {
                if(this.categoriesList.get(i) == this.categoriesList.get(j)) {
                    mostCommon = this.categoriesList.get(i);
                }
            }
        }
        
        return mostCommon;
    }

    public List<Responses.RecipeID> filterByCategory() throws IOException {
        String url = "https://themealdb.com/api/json/v1/1/filter.php?c=" + this.mostCommonCategory();
        List<Responses.RecipeID> filteredByCategory = this.recipeUtils.callAPI(url, Responses.RecipeID.class);

        return filteredByCategory;
    }

    public List<RecipeID> filterByArea() throws IOException {
        List<RecipeID> generatedList = new ArrayList<>();

        List<RecipeID> filteredByCategory = this.filterByCategory();

        for(RecipeID meal : filteredByCategory) {
            String url = "https://themealdb.com/api/json/v1/1/lookup.php?i=" + meal.toString();
            List<RecipeID> singleMeal = this.recipeUtils.callAPI(url, RecipeID.class);

            for(Area area : this.areasList) {
                if(area == this.recipeUtils.getArea(meal)) {
                    generatedList.add(singleMeal.get(0)); // it will only have one item in it anyways
                }
            }
        }

        return generatedList;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        try{
            if(request.queryParams().size() == 0) {
                return serialize(viewFailureResponse("error_bad_request", "no endpoint was found"));
            }
            String string1 = request.queryParams("1");
            String string2 = request.queryParams("2");
            String string3 = request.queryParams("3");
            String string4 = request.queryParams("4");
            String string5 = request.queryParams("5");
            System.out.println("got query params");
            System.out.println(string1);
            RecipeID id1 = this.recipeUtils.fromJson(RecipeID.class, string1);
            System.out.println(id1);
            RecipeID id2 = this.recipeUtils.fromJson(RecipeID.class, string2);
            RecipeID id3 = this.recipeUtils.fromJson(RecipeID.class, string3);
            RecipeID id4 = this.recipeUtils.fromJson(RecipeID.class, string4);
            RecipeID id5 = this.recipeUtils.fromJson(RecipeID.class, string5);
            System.out.println("converted string to id");
            this.mealIDs = new ArrayList<RecipeID>(List.of(id1, id2, id3, id4, id5));
            System.out.println(this.mealIDs);



            //todo: add a catch where it handles an invalid ID

            this.getAllCategories();
            System.out.println("got all categories");
            this.getAllAreas();
            System.out.println("got all areas");
            List<RecipeID> generatedList = this.filterByArea();
            System.out.println("this is the list " + generatedList);
            return serialize(viewSuccessResponse(generatedList));

        }catch(Exception e){
            return serialize(viewFailureResponse("error_bad_request", e.getMessage()));
        }


        

    }

    private Map<String, Object> viewSuccessResponse(List<RecipeID> ids) {
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
