package edu.brown.cs32.student.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

import edu.brown.cs32.student.main.server.JSONReader;


public class JSONReaderTests{

@Test
public void testGeoJSON() throws IOException{
    String jsonString = "{\n"
+ "   \"type\": \"Feature\",\n"
+ "   \"geometry\": {\n"
+ "     \"type\": \"Point\",\n"
+ "     \"coordinates\": [125.6, 10.1]\n"
+ " },\n "
+ "   \"properties\": {\n"
+ "     \"name\": \"Dinagat Islands\"\n"
+ " }\n"
+ "}\n";

    JSONReader reader = new JSONReader(jsonString);

    Map<String, Object> jsonMap = reader.readJSON(jsonString);
    assertEquals(jsonMap.get("type").toString(), "Feature");
    assertEquals(jsonMap.get("geometry").toString(), "{type=Point, coordinates=[125.6, 10.1]}");
    assertEquals(jsonMap.get("properties").toString(), "{name=Dinagat Islands}");
}


@Test
public void testGeoJSONInnerElements() throws IOException{
    String jsonString = "{\n"
+ "   \"type\": \"Feature\",\n"
+ "   \"geometry\": {\n"
+ "     \"type\": \"Point\",\n"
+ "     \"coordinates\": [125.6, 10.1]\n"
+ " },\n "
+ "   \"properties\": {\n"
+ "     \"name\": \"Dinagat Islands\"\n"
+ " }\n"
+ "}\n";

    JSONReader reader = new JSONReader(jsonString);

    Map<String, Object> jsonMap = reader.readJSON(jsonString);
    Map<String, Object> geometryMap = (Map<String, Object>) jsonMap.get("geometry");
    assertEquals(geometryMap.get("type").toString(), "Point");
    assertEquals(geometryMap.get("coordinates").toString(), "[125.6, 10.1]");
}

@Test
public void testFakeJSON() throws IOException{
    String jsonString = "{\n"
+ "   \"fruit\": \"strawberry\",\n"
+ "   \"vegetable\": {\n"
+ "     \"type\": \"broccoli\",\n"
+ "     \"rating\": 5.6\n"
+ " },\n "
+ "   \"salad\": {\n"
+ "     \"ingredients\": \"6\"\n"
+ " }\n"
+ "}\n";

    JSONReader reader = new JSONReader(jsonString);

    Map<String, Object> jsonMap = reader.readJSON(jsonString);
    Map<String, Object> veggieMap = (Map<String, Object>) jsonMap.get("vegetable");
    assertEquals(veggieMap.get("type").toString(), "broccoli");
    assertEquals(veggieMap.get("rating"), 5.6);
    assertEquals(jsonMap.get("salad").toString(), "{ingredients=6}");
}

}
