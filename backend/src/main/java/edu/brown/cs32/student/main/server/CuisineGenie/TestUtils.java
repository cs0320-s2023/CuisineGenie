//package edu.brown.cs32.student.main.server.CuisineGenie;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import edu.brown.cs32.student.main.server.CuisineGenie.Responses.Meals;
//import spark.utils.Assert;
//
//public class TestUtils {
//  private Generator g;
//  private RecipeUtils rUtils;
//  private RecipeUtils recipeUtils;
//  private List<Meals> mealIDs;
//  private List<String> categoriesList;
//
//  public TestUtils(){
//    this.g = new Generator();
//    this.recipeUtils = new RecipeUtils();
//    this.categoriesList = new ArrayList<String>();
//
//
//  }
//
////  @Test
////  public void testListUtils() throws IOException {
////    Meals id1 = this.recipeUtils.callAPI(
////        "https://themealdb.com/api/json/v1/1/lookup.php?i=", "52819", Meals.class);
////    System.out.println(id1);
////    Meals id2 = this.recipeUtils.callAPI(
////        "https://themealdb.com/api/json/v1/1/lookup.php?i=", "52777", Meals.class);
////    Meals id3 = this.recipeUtils.callAPI(
////        "https://themealdb.com/api/json/v1/1/lookup.php?i=", "52836", Meals.class);
////    Meals id4 = this.recipeUtils.callAPI(
////        "https://themealdb.com/api/json/v1/1/lookup.php?i=", "52882", Meals.class);
////    Meals id5 = this.recipeUtils.callAPI(
////        "https://themealdb.com/api/json/v1/1/lookup.php?i=", "52975", Meals.class);
////     this.mealIDs = new ArrayList<Meals>(List.of(id1, id2, id3, id4, id5));
////     this.g.getAllCategories();
////
////    Assert.notNull(this.categoriesList);
////
////
////  }
//
//}
