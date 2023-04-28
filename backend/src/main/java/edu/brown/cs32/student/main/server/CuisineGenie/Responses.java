package edu.brown.cs32.student.main.server.CuisineGenie;

import com.squareup.moshi.Json;

public class Responses {

    public record RecipeID(@Json(name="idMeal") double idMeal){
    }

    public record Ingredients(
            @Json(name="strIngredient1") String ingredient1,
            @Json(name="strIngredient2") String ingredient2,
            @Json(name="strIngredient3") String ingredient3
    ){}

//    public record IngredientOne(@Json(name="strIngredient1") String ingredient1){
//    }
//
//    public record IngredientTwo(@Json(name="strIngredient2") String ingredient2){
//    }
//
//    public record IngredientThree(@Json(name="strIngredient3") String ingredient3){
//    }

}
