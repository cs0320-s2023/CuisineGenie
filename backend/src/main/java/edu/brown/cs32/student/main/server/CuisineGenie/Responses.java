package edu.brown.cs32.student.main.server.CuisineGenie;

import com.squareup.moshi.Json;
import java.util.List;

/**
 * Responses classes for our Meals and MealProperties Records
 */

public class Responses {

    /**
     * Our Meals and MealProperties records allow us to convert API results into an object
     * MealProperties allows us to access Meals Data
     */

    public record Meals(@Json(name="meals") List<MealProperties> mealProperties) {}
    public record MealProperties(@Json(name="idMeal") String mealID,
                                 @Json(name="strCategory") String category,
                                 @Json(name="strArea") String area) {}




}
