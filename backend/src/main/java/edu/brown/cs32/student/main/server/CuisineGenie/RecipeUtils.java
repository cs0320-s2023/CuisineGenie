package edu.brown.cs32.student.main.server.CuisineGenie;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeUtils {

    // gets top 3 ingredients of a recipe
    public List<String> getIngredients(Responses.RecipeID recipeID) throws IOException {
        String endpoint = this.getRecipeEndpointURL(recipeID);

        Responses.Ingredients ingredients = this.callAPI(endpoint, Responses.Ingredients.class);
        String ingredient1 = ingredients.ingredient1();
        String ingredient2 = ingredients.ingredient2();
        String ingredient3 = ingredients.ingredient3();

        List<String> ingredientsList = new ArrayList<>();
        ingredientsList.add(ingredient1);
        ingredientsList.add(ingredient2);
        ingredientsList.add(ingredient3);

        return ingredientsList;
    }

    // Makes an API call to the given URL and converts the response to the specified class using moshi
    public <T> T callAPI(String url, Type classType) throws IOException {
        URL endpoint = new URL(url);
        HttpURLConnection clientConnection = (HttpURLConnection) endpoint.openConnection();

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

    public String getRecipeEndpointURL(Responses.RecipeID recipeID) {
        double idMeal = recipeID.idMeal();
        return "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + idMeal;
    }

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

    public <T> T fromJson(Type classType, String jsonString) throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<T> adapter = moshi.adapter(classType);
        return adapter.fromJson(jsonString);
    }
}
