package edu.brown.cs32.student.main.server.weather;

/**
 * A class containing useful records to store weather input/output data.
 */
public class WeatherUtil {
  public record Coordinates(Double latitude, Double longitude, Double maxDiff) {

    public Double getLatitude() {
      return this.latitude;
    }

    public Double getLongitude() {
      return this.longitude;
    }

    private double getDist(Coordinates other) {
      return Math.sqrt(Math.pow(this.latitude - other.getLatitude(), 2) +
          Math.pow(this.longitude - other.getLongitude(), 2));
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) {
        return true;
      }
      if (!(other instanceof Coordinates otherCoords)) {
        return false;
      }

      return (this.getDist(otherCoords) <= this.maxDiff);
    }

  }

  public record WeatherRecord(int temp, String unit, String timestamp) {
    public int temp() {
      return temp;
    }

    public String unit() {
      return unit;
    }

    public String timestamp() {
      return timestamp;
    }
  }

}
