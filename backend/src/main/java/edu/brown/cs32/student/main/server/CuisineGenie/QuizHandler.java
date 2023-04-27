package edu.brown.cs32.student.main.server.CuisineGenie;


import java.util.ArrayList;
import java.util.List;

public class QuizHandler {
    final private String nameURL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=";
    final private String ingredientURL = "https://www.themealdb.com/api/json/v1/1/filter.php?i=";

    private List<Integer> mealIDs = List.of(1, 2, 3, 4, 5);

    public ArrayList<String> filter() {
        for(Integer meal : mealIDs) {
            String url = nameURL + meal;

        }
        String url = ingredientURL + ???
    }
}
