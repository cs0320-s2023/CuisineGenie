package edu.brown.cs32.student.main.server.CuisineGenie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Recommender {

    private List<Responses.RecipeID> mealIDs; // this will be given to us by the frontend
    private RecipeUtils recipeUtils;

    public Recommender() {
        this.recipeUtils = new RecipeUtils();
    }
    public List<Responses.RecipeID> filter() throws IOException {
        List<Responses.RecipeID> recommendedList = new ArrayList<>();

        for(Responses.RecipeID meal : mealIDs) {
            List<String> ingredientsList = this.recipeUtils.getIngredients(meal);

            for(String ingredient : ingredientsList) {
                String ingredientURL = "https://www.themealdb.com/api/json/v1/1/filter.php?i=" + ingredient;

                // confused about what this would return??
                // an ingredient can have more than one meal
                List<Responses.RecipeID> mealsList = this.recipeUtils.callAPI(ingredientURL, Responses.RecipeID.class);
                Random rand = new Random();
                int randomIndex = rand.nextInt(mealsList.size());
                Responses.RecipeID recommendedMeal = mealsList.get(randomIndex);
                recommendedList.add(recommendedMeal);
            }
        }

        return recommendedList;
    }

    public void dislike() {

    }
}
