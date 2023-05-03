package edu.brown.cs32.student.main.server.CuisineGenie;

import com.squareup.moshi.Json;

public class Responses {

//    public record RecipeID(@Json(name="idMeal") String idMeal){
//    }
public record RecipeID(String idMeal){
}


    public record Category(@Json(name="strCategory") String category){
    }

    public record Area(@Json(name="strArea") String area) {  
    }

    // public record Ingredients(
    //         @Json(name="strIngredient1") String ingredient1,
    //         @Json(name="strIngredient2") String ingredient2,
    //         @Json(name="strIngredient3") String ingredient3
    // ){}

//    public record IngredientOne(@Json(name="strIngredient1") String ingredient1){
//    }
//
//    public record IngredientTwo(@Json(name="strIngredient2") String ingredient2){
//    }
//
//    public record IngredientThree(@Json(name="strIngredient3") String ingredient3){
//    }

}
