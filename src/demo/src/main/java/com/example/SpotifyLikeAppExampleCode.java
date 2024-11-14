package com.example;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

// declares a class for the app
public class SpotifyLikeAppExampleCode {

  // the current audio clip
  private static Clip audioClip;

  // Current Dirrectory Path
  private static String directoryPath = "C:\\Users\\josep\\OneDrive\\Desktop\\Git Repo\\Spotify-like-app\\src\\demo\\src\\main\\java\\com\\example\\";

  // String Array to display "Recently Played" in Home menu
  private static ArrayList<String> recentlyPlayed = new ArrayList<String>();

  // create a scanner for user input
  private static Scanner input = new Scanner(System.in);

  // Array of indices to favorite songs in the library
  private static ArrayList<Integer> favoriteSongs = new ArrayList<Integer>();

  // "main" makes this class a java app that can be executed
  public static void main(final String[] args) {
    // reading audio library from json file
    Song[] library = readAudioLibrary();

    String userInput = "";
    while (!userInput.equals("q")) {
      menu();

      // get input
      userInput = input.nextLine();

      // accept upper or lower case commands
      userInput = userInput.toLowerCase();

      // do something
      handleMenu(userInput, library);
    }

    // close the scanner
    input.close();
  }

  /*
   * displays the menu for the app
   */
  public static void menu() {
    System.out.println("---- SpotifyLikeApp ----");
    System.out.println("[H]ome");
    System.out.println("[S]earch by title");
    System.out.println("[L]ibrary");
    System.out.println("[F]avorites");
    System.out.println("[Q]uit");
    System.out.println("");
    System.out.print("Enter q to Quit:");
  }

  // Prints out a list of recently played songs
  public static void home() {
    System.out.println("Recently Played: ");
    for (int i = 0; i < recentlyPlayed.size(); i++) {
      System.out.println(i + 1 + ": " + recentlyPlayed.get(i));
    }
  }

  // Searches for song name inputed through Song array and plays it if found
  public static void search(Song[] library) {
    System.out.println("Please enter song title");
    Scanner input = new Scanner(System.in);
    String title = input.nextLine();
    for (int i = 0; i < library.length; i++) {
      if (title.equalsIgnoreCase(library[i].name())) {
        play(library, i);
        break;
      }
    }
  }

  // Prints library of songs and plays the user-selected song
  public static void library(Song[] library) {
    for (int i = 0; i < library.length; i++) {
      System.out.println(i + 1 + ": " + library[i].name() + " by " + library[i].artist());
    }
    Scanner input = new Scanner(System.in);
    System.out.println("Please select which song to play");
    int index = input.nextInt();
    play(library, index - 1);
  }

  // Prints favorited songs and plays the user-selected song
  public static void favorites(Song[] library){
    if(favoriteSongs.isEmpty()){
      System.out.println("You have no favorited songs");
      return;
    }

    System.out.println("Favorited Songs: ");
    for (int i = 0; i < favoriteSongs.size(); i++) {
      System.out.println(i + 1 + ": " + library[favoriteSongs.get(i)].name() + " by " + library[favoriteSongs.get(i)].artist());
    }

    Scanner input = new Scanner(System.in);
    System.out.println("Please select which song to play");
    int index = input.nextInt();
    play(library, favoriteSongs.get(index-1));

  }


  /*
   * handles the user input for the app
   */
  public static void handleMenu(String userInput, Song[] library) {
    switch (userInput) {
      case "h":
        System.out.println("-->Home<--");
        home();
        break;
      case "s":
        System.out.println("-->Search by title<--");
        search(library);
        break;
      case "l":
        System.out.println("-->Library<--");
        library(library);
        break;
      case "f":
        System.out.println("-->Favorites<--");
        favorites(library);
        break;
      case "q":
        System.out.println("-->Quit<--");
        break;
      default:
        break;
    }
  }

  /*
   * plays an audio file
   */
  public static void play(Song[] library, int index) {
    // open the audio file

    // get the filePath and open a audio file
    final Integer i = index;
    final String filename = library[i].fileName();
    final String filePath = directoryPath + "/wav/" + filename;
    final File file = new File(filePath);

    // stop the current song from playing, before playing the next one
    if (audioClip != null) {
      audioClip.close();
    }

    try {
      // create clip
      audioClip = AudioSystem.getClip();

      // get input stream
      final AudioInputStream in = AudioSystem.getAudioInputStream(file);

      audioClip.open(in);
      audioClip.setMicrosecondPosition(0);
      audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // Adds song to Recently played list
    recentlyPlayed.add(library[i].name() + " by " + library[i].artist());
    
    String userInput = "";
    
    while (!userInput.equals("B")) {
    // Prints information of song when it starts playing
    System.out.println("---------------------");
    System.out.println("Currently Playing: ");
    System.out.println("Song name: " + library[i].name());
    System.out.println("Artist name: " + library[i].artist());
    System.out.println("Genre: " + library[i].genre());
    System.out.println("Release Date: " + library[i].year());
    System.out.println("Favorited: " + library[i].isFavortite());
    System.out.println("File path: " + library[i].fileName());
    System.out.println("---------------------");
    if(library[i].isFavortite()){
      System.out.println("[Un]favorite");
    } else{
      System.out.println("[Fa]vorite");
    }
    System.out.println("[Fo]ward 5 Seconds");
    System.out.println("[R]ewind 5 Seconds");
    System.out.println("[B]ack");
    System.out.println("---------------------");

    Scanner input = new Scanner(System.in);  
    userInput = input.nextLine();

    switch (userInput) {
      case "Fa":
        favoriteSongs.add(index);
        library[i].setFavorite();
        break;

      case "Un":
        favoriteSongs.remove(Integer.valueOf(index));
        library[i].setFavorite();
        break;
       
      case "Fo":
        audioClip.setMicrosecondPosition(audioClip.getMicrosecondPosition() + 5000000);
        break;

      case "R":
        audioClip.setMicrosecondPosition(audioClip.getMicrosecondPosition() - 5000000);
        break;
        
      default:
        break;
    }
  }
  }

  // read the audio library of music
  public static Song[] readAudioLibrary() {
    // get the file path
    final String jsonFileName = "audio-library.json";
    final String filePath = directoryPath + "/" + jsonFileName;

    Song[] library = null;
    try {
      System.out.println("Reading the file " + filePath);
      JsonReader reader = new JsonReader(new FileReader(filePath));
      library = new Gson().fromJson(reader, Song[].class);
    } catch (Exception e) {
      System.out.printf("ERROR: unable to read the file %s\n", filePath);
      System.out.println();
    }

    return library;
  }
}
