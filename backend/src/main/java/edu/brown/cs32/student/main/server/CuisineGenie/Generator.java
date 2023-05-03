package edu.brown.cs32.student.main.server.CuisineGenie;
//
//import edu.brown.cs32.student.main.server.CuisineGenie.Responses.Area;
//import edu.brown.cs32.student.main.server.CuisineGenie.Responses.RecipeID;
import edu.brown.cs32.student.main.server.CuisineGenie.Responses.MealProperties;
import edu.brown.cs32.student.main.server.CuisineGenie.Responses.Meals;
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

    private List<Meals> mealIDs; // this will be given to us by the frontend
    private RecipeUtils recipeUtils;
    private List<String> categoriesList;
    private List<String> areasList;

    public Generator() {
        this.recipeUtils = new RecipeUtils();
        this.categoriesList = new ArrayList<String>();
        this.areasList = new ArrayList<String>();
    }

    public void getAllCategories() throws IOException {


        try {
            for(Meals meal : this.mealIDs) {
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

    public void getAllAreas() throws IOException {
        try{
            for(Meals meal : mealIDs) {
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

        // nothing in common
        Random r = new Random();
        int randomItem = r.nextInt(categoriesList.size());
        String  mostCommon = categoriesList.get(randomItem);

        for(int i = 0; i < this.categoriesList.size(); i++) {
            for(int j = 1; j < this.categoriesList.size() - 1; j++) {
                if(this.categoriesList.get(i) == this.categoriesList.get(j)) {
                    mostCommon = this.categoriesList.get(i);
                }
            }
        }
        
        return mostCommon;
    }

    public List<MealProperties> filterByCategory() throws IOException {
        //String url = "https://themealdb.com/api/json/v1/1/filter.php?c=" + this.mostCommonCategory();
        List<MealProperties> filteredByCategory = new ArrayList<>();
        System.out.println(this.mostCommonCategory());
        Meals mealsFromCategory = this.recipeUtils.callAPI("https://themealdb.com/api/json/v1/1/filter.php?c=", this.mostCommonCategory(), Meals.class);

        for(MealProperties meal : mealsFromCategory.mealProperties())

            filteredByCategory.add(meal);


        return filteredByCategory;
    }

    public List<String> filterByArea() throws IOException {
        System.out.println("in filter by area");
        List<String> generatedList = new ArrayList<>();
        System.out.println("made generated list");

        List<MealProperties> filteredByCategory = this.filterByCategory();
        System.out.println("List of meals filtered by category: " + filteredByCategory);

        List<Meals> withCategoryAndArea = new ArrayList<>();
        for(MealProperties meal : filteredByCategory) {
            String id = meal.mealID();
            System.out.println((id));
            Meals singleMeal = this.recipeUtils.callAPI("https://themealdb.com/api/json/v1/1/lookup.php?i=", id, Meals.class);
//            System.out.println("the single meal: " + singleMeal)
            MealProperties mealProps = this.recipeUtils.callAPI("https://themealdb.com/api/json/v1/1/lookup.php?i=", id, MealProperties.class);
            singleMeal.mealProperties().add(mealProps);

            withCategoryAndArea.add(singleMeal);
        }
        System.out.println(withCategoryAndArea);

        System.out.println(this.areasList);

        for(Meals meal : withCategoryAndArea) {
            System.out.println(meal.mealProperties().get(0).area());

            for(String area : this.areasList) {
                if(area.equals(meal.mealProperties().get(0).area())) {
                    System.out.println("hi");
                    generatedList.add(meal.mealProperties().get(0).mealID()); // it will only have one item in it anyways
                }
            }
        }

        System.out.println("this is the generated list of the mealIDs we will display: " + generatedList);


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
            Meals id1 = this.recipeUtils.callAPI("https://themealdb.com/api/json/v1/1/lookup.php?i=", string1, Meals.class);
            System.out.println(id1);
            Meals id2 = this.recipeUtils.callAPI("https://themealdb.com/api/json/v1/1/lookup.php?i=", string2, Meals.class);
            Meals id3 = this.recipeUtils.callAPI("https://themealdb.com/api/json/v1/1/lookup.php?i=", string3, Meals.class);
            Meals id4 = this.recipeUtils.callAPI("https://themealdb.com/api/json/v1/1/lookup.php?i=", string4, Meals.class);
            Meals id5 = this.recipeUtils.callAPI("https://themealdb.com/api/json/v1/1/lookup.php?i=", string5, Meals.class);


//            Meals id2 = this.recipeUtils.fromJson(Meals.class, string2);
//            Meals id3 = this.recipeUtils.fromJson(Meals.class, string3);
//            Meals id4 = this.recipeUtils.fromJson(Meals.class, string4);
//            Meals id5 = this.recipeUtils.fromJson(Meals.class, string5);
            System.out.println("converted string to id");
            this.mealIDs = new ArrayList<Meals>(List.of(id1, id2, id3, id4, id5));
            System.out.println(this.mealIDs);



            //todo: add a catch where it handles an invalid ID

            this.getAllCategories();
            System.out.println("got all categories");
            this.getAllAreas();
            System.out.println("got all areas");
            List<String> generatedList = this.filterByArea();

            System.out.println("this is the list " + generatedList);
            return serialize(viewSuccessResponse(generatedList));

        }catch(Exception e){
            return serialize(viewFailureResponse("error_bad_request", e.getMessage()));
        }


        

    }

    private Map<String, Object> viewSuccessResponse(List<String> ids) {
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
