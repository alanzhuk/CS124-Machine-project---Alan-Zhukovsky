package edu.illinois.cs.cs124.ay2022.mp.models;
import android.nfc.Tag;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
 * Model storing information about a place retrieved from the backend server.
 *
 * You will need to understand some of the code in this file and make changes starting with MP1.
 */
@SuppressWarnings("unused")
public final class Place {
  /*
   * The Jackson JSON serialization library that we are using requires an empty constructor.
   * So don't remove this!
   */
  public Place() {}

  public Place(
      final String setId,
      final String setName,
      final double setLatitude,
      final double setLongitude,
      final String setDescription) {
    id = setId;
    name = setName;
    latitude = setLatitude;
    longitude = setLongitude;
    description = setDescription;
  }

  // ID of the place
  private String id;

  public String getId() {
    return id;
  }

  // Name of the person who submitted this favorite place
  private String name;

  public String getName() {
    return name;
  }

  // Latitude and longitude of the place
  private double latitude;

  public double getLatitude() {
    return latitude;
  }

  private double longitude;

  public double getLongitude() {
    return longitude;
  }

  // Description of the place
  private String description;

  public String getDescription() {
    return description;
  }

  public static List<Place> search(final List<Place> places, final String search) {
    if (places == null || search == null) {
      throw new IllegalArgumentException();
    }
    if (places.size() == 0 || search.equals("") || search.split(" ").length == 0) {
      return places;
    }
    String sHold = search.trim().toLowerCase();
    List<Place> ret = new ArrayList<>();
    List<Place> filtered = new ArrayList<>();
    List<String> filter = new ArrayList<>();
    for (String w : search.split(" ")) {
      if (w.substring(0, 1).equals("-")) {
        filter.add(w.substring(1, w.length()).toLowerCase());
      }
    }
    String toPrint = "";
    for (String i : filter) {
      toPrint += i;
    }
    for (Place i: places) {
      boolean cont = false;
      for (String w : filter) {
        if (i.getDescription().toLowerCase().contains(w)) {
          filtered.add(i);
          cont = true;
          break;
        }
      }
      if (cont) {
        continue;
      }
      boolean notYet = true;
      String word = "";
      for (char w: i.getDescription().toLowerCase().toCharArray()) {
        if (w == '.' || w == '!' || w == '?' || w == ',' || w == ':' || w == ';' || w == '/' || w == ' ') {
          word += " ";
        } else if (Character.isAlphabetic(w) || Character.isDigit(w)) {
          word += w;
        }
      }
      for (String w: word.split(" ")) {
        if (sHold.equals(w) && notYet) {
          ret.add(i);
          notYet = false;
        }
      }
    }
    if (ret.size() == 0 && filtered.size() != 0) {
      for (Place i : places) {
        if (!(filtered.contains(i))) {
          ret.add(i);
        }
      }
    }
    return ret;
  }
}
