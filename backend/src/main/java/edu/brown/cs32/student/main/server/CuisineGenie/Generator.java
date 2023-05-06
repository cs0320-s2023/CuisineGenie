package edu.brown.cs32.student.main.server.CuisineGenie;
//
//import edu.brown.cs32.student.main.server.CuisineGenie.Responses.Area;
//import edu.brown.cs32.student.main.server.CuisineGenie.Responses.RecipeID;
import edu.brown.cs32.student.main.server.CuisineGenie.Responses.MealProperties;
import edu.brown.cs32.student.main.server.CuisineGenie.Responses.Meals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
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

public class Generator implements Route {

    private List<Meals> mealIDs; // this will be given to us by the frontend
    private RecipeUtils recipeUtils;
    private List<String> categoriesList;
    private List<String> areasList;

    public Generator() {
        this.recipeUtils = new RecipeUtils();
        this.categoriesList = new ArrayList<String>();
        this.areasList = new ArrayList<String>();
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {
            if (request.queryParams().size() != 5) {
                return serialize(
                    viewFailureResponse("error_bad_request",
                        "Please input 5 recipes IDs."));
            }
            String string1 = request.queryParams("1");
            String string2 = request.queryParams("2");
            String string3 = request.queryParams("3");
            String string4 = request.queryParams("4");
            String string5 = request.queryParams("5");
            System.out.println("got query params");
            System.out.println(string1);
            Meals id1 = this.recipeUtils.callAPI(
                "https://themealdb.com/api/json/v1/1/lookup.php?i=", string1, Meals.class);
            System.out.println(id1);
            Meals id2 = this.recipeUtils.callAPI(
                "https://themealdb.com/api/json/v1/1/lookup.php?i=", string2, Meals.class);
            Meals id3 = this.recipeUtils.callAPI(
                "https://themealdb.com/api/json/v1/1/lookup.php?i=", string3, Meals.class);
            Meals id4 = this.recipeUtils.callAPI(
                "https://themealdb.com/api/json/v1/1/lookup.php?i=", string4, Meals.class);
            Meals id5 = this.recipeUtils.callAPI(
                "https://themealdb.com/api/json/v1/1/lookup.php?i=", string5, Meals.class);

            if (id1 == null || id2 == null || id3 == null || id4 == null || id5 == null) {
                return serialize(viewFailureResponse("error_bad_request",
                    "Please input valid Recipe IDs from the MealDB API."));
            }

            System.out.println("converted string to id");
            this.mealIDs = new ArrayList<Meals>(List.of(id1, id2, id3, id4, id5));
            System.out.println("mealIDs from query params:" + this.mealIDs);
            this.getAllCategories();
            System.out.println("got all categories");
            this.getAllAreas();
            System.out.println("got all areas");
            List<String> generatedList = this.filterByArea(); // Display a copy of the list
            System.out.println("this is the list " + generatedList);
            return serialize(viewSuccessResponse(generatedList));

        } catch (IOException e) {
            return serialize(viewFailureResponse("error_datasource",
                "Please enter a valid Recipe ID to be converted."));
        }

    }

    public void getAllCategories() {
        for (Meals meal : this.mealIDs) {
            String category = this.recipeUtils.getCategory(meal);
            this.categoriesList.add(category);
        }
    }


    public void getAllAreas() {
        for (Meals meal : mealIDs) {
            String area = this.recipeUtils.getArea(meal);

            if (this.areasList.contains(area) == false) {
                this.areasList.add(area);
            }
        }
    }


    public String mostCommonCategory() {
        if (this.categoriesList == null || this.categoriesList.isEmpty()) {
            return null;
        }

        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String str : this.categoriesList) {
            frequencyMap.put(str, frequencyMap.getOrDefault(str, 0) + 1);
        }

        String mostCommonString = null;
        int highestFrequency = 0;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > highestFrequency) {
                mostCommonString = entry.getKey();
                highestFrequency = entry.getValue();
            }
        }

        if (highestFrequency == 1) {
            Random random = new Random();
            List<String> keys = new ArrayList<>(frequencyMap.keySet());
            return keys.get(random.nextInt(keys.size()));
        }
        System.out.println("most common string : " + mostCommonString);

        return mostCommonString;


    }

    public List<MealProperties> filterByCategory() throws IOException {
        try {
            //String url = "https://themealdb.com/api/json/v1/1/filter.php?c=" + this.mostCommonCategory();
            List<MealProperties> filteredByCategory = new ArrayList<>();
//        System.out.println("most common cat list: "+  this.mostCommonCategory());
            Meals mealsFromCategory = this.recipeUtils.callAPI(
                "https://themealdb.com/api/json/v1/1/filter.php?c=", this.mostCommonCategory(),
                Meals.class);
            for (MealProperties meal : mealsFromCategory.mealProperties())
                filteredByCategory.add(meal);
            return filteredByCategory;

        } catch (IOException e) {
            System.err.println("Please provide a valid category to search by");
            return null;
        }

    }


    public List<String> filterByArea() throws IOException {
        try {
            System.out.println("in filter by area");
            List<String> generatedList = new ArrayList<>();
            System.out.println("made generated list");

            List<MealProperties> filteredByCategory = this.filterByCategory();
            System.out.println("List of meals filtered by category: " + filteredByCategory);

            List<Meals> withCategoryAndArea = new ArrayList<>();
            for (MealProperties meal : filteredByCategory) {
                String id = meal.mealID();
                // System.out.println((id));
                Meals singleMeal = this.recipeUtils.callAPI(
                    "https://themealdb.com/api/json/v1/1/lookup.php?i=", id, Meals.class);
//            System.out.println("the single meal: " + singleMeal)
                MealProperties mealProps = this.recipeUtils.callAPI(
                    "https://themealdb.com/api/json/v1/1/lookup.php?i=", id, MealProperties.class);
                singleMeal.mealProperties().add(mealProps);

                withCategoryAndArea.add(singleMeal);
            }
            System.out.println("With category and area list:" + withCategoryAndArea);

            System.out.println("This is the areas list:" + this.areasList);

            for (Meals meal : withCategoryAndArea) {
                System.out.println(meal.mealProperties().get(0).area());

                for (String area : this.areasList) {
                    if (area.equals(meal.mealProperties().get(0).area())) {
                        System.out.println("hi");
                        generatedList.add(meal.mealProperties().get(0)
                            .mealID()); // it will only have one item in it anyways
                    }
                }
            }

            System.out.println(
                "this is the generated list of the mealIDs we will display: " + generatedList);

            return generatedList;

        } catch (IOException e) {
            System.err.println("Please provide a valid Recipe ID to lookup");
            return null;

        }
    }



    private Map<String, Object> viewSuccessResponse(List<String> ids) {
        Map<String, Object> responses = new HashMap<>();
        responses.put("result", "success");
        responses.put("ids", ids); // for frontend to fetch
        System.out.println(ids);
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
