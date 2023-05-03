package edu.brown.cs32.student.main.server;

import static spark.Spark.after;

import edu.brown.cs32.student.main.server.CuisineGenie.Generator;
import edu.brown.cs32.student.main.server.handlers.LoadHandler;
import edu.brown.cs32.student.main.server.handlers.SearchHandler;
import edu.brown.cs32.student.main.server.handlers.ViewHandler;

import edu.brown.cs32.student.main.server.handlers.WeatherHandler;
import java.util.ArrayList;
import java.util.List;
import spark.Spark;
import java.util.HashSet;
import java.util.Set;

/**
 * Top-level class for this demo. Contains the main() method which starts Spark
 * and runs the various handlers.
 *
 * We have two endpoints in this demo. They need to share state (a menu).
 * This is a great chance to use dependency injection, as we do here with the
 * menu set. If we needed more endpoints,
 * more functionality classes, etc. we could make sure they all had the same
 * shared state.
 */
public class Server {

    public static void main(String[] args) {
        List<List<String>> csv = new ArrayList<>();
        Spark.port(61747);
        after((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "*");
        });

        // ENDPOINTS
        Spark.get("loadcsv", new LoadHandler(csv));
        Spark.get("viewcsv", new ViewHandler(csv));
        Spark.get("searchcsv", new SearchHandler(csv));
        Spark.get("weather", new WeatherHandler(0.5));
        Spark.get("generaterecipes", new Generator());

        Spark.init();
        Spark.awaitInitialization();
        System.out.println("Server started.");
    }

}
