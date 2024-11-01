package com.example;

public class Song {

  private String name;
  private String artist;
  private String year;
  private String genre;
  private String fileName;

  // serializes attributes into a string
  public String toString() {
    String s;

    // since the object is complex, we return a JSON formatted string
    s = "{ ";
    s += "name: " + name;
    s += ", ";
    s += "artist: " + artist;
    s += ", ";
    s += "year: " + year;
    s += ", ";
    s += "genre: " + genre;
    s += ", "; 
    s += "fileName: " + fileName;
    s += " }";

    return s;
  }

  // getters
  public String name() {
    return this.name;
  }

  public String artist() {
    return this.artist;
  }

  public String year() {
    return this.year;
  }

  public String genre() {
    return this.genre;
  }

  public String fileName() {
    return this.fileName;
  }
}
