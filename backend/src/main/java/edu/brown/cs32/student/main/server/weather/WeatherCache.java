package edu.brown.cs32.student.main.server.weather;

import edu.brown.cs32.student.main.server.weather.WeatherUtil.Coordinates;
import edu.brown.cs32.student.main.server.weather.WeatherUtil.WeatherRecord;
import edu.brown.cs32.student.main.server.weather.NWSRequester;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;

/**
 * Proxy between server and NWS API. Caches weather queries.
 */
public class WeatherCache {
  private final LoadingCache<Coordinates, WeatherRecord> cache;

  public WeatherCache(Integer expireDuration, TimeUnit expireDurationUnit) {
    this.cache = CacheBuilder.newBuilder()
        .maximumSize(10)
        .expireAfterWrite(
            expireDuration,
            expireDurationUnit)
        .recordStats()
        .build(
            new CacheLoader<Coordinates, WeatherRecord>() {
              @Override
              public WeatherRecord load(Coordinates coordinates) throws Exception {
                NWSRequester requester = new NWSRequester(coordinates);
                return requester.makeRequest();
              }
            });

  }

  /**
   * Finds the nearest coord in the cache within the cache's maxDist, if it
   * exists.
   * 
   * @param inputCoord the coord to search for
   * @return this coord, or the closest one already cached
   */
  public Coordinates getNearestCoord(Coordinates inputCoord) {
    Map<Coordinates, WeatherRecord> cacheMap = cache.asMap();
    for (Coordinates coord : cacheMap.keySet()) {
      if (coord.equals(inputCoord)) {
        return coord;
      }
    }
    return inputCoord;
  }

  /**
   * The proxy between calling the NWS API and our server; checks cache before
   * querying NWS.
   * 
   * @param inputCoord the coord to find weather data for
   * @return a WeatherRecord containing weather data
   */
  public WeatherRecord queryWeather(Coordinates inputCoord) {
    Coordinates nearestCoord = getNearestCoord(inputCoord);
    WeatherRecord res = cache.getUnchecked(nearestCoord);
    return res;
  }

}
