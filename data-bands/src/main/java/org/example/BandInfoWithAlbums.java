package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BandInfoWithAlbums {

    private static final String API_KEY = "2";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Band Info + Albums!");
        System.out.print("Enter your favorite band: ");
        String bandName = scanner.nextLine();

        String artistId = getBandInfo(bandName);

        if (artistId != null) {
            getAlbums(artistId);
        }

        scanner.close();
    }

    // Fetch band info and return artistId
    public static String getBandInfo(String bandName) {
        try {
            String formattedName = bandName.trim().replace(" ", "_");
            String urlString = "https://www.theaudiodb.com/api/v1/json/" + API_KEY + "/search.php?s=" + formattedName;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();

            if (json.get("artists").isJsonNull()) {
                System.out.println("Band not found. Try another name.");
                return null;
            }

            JsonObject artist = json.getAsJsonArray("artists").get(0).getAsJsonObject();

            System.out.println("\nBand Information?");
            System.out.println("- Name: " + artist.get("strArtist").getAsString());
            System.out.println("- Formed Year: " + artist.get("intFormedYear").getAsString());
            System.out.println("- Country: " + artist.get("strCountry").getAsString());
            System.out.println("- Genre: " + artist.get("strGenre").getAsString());
            System.out.println("- Style: " + artist.get("strStyle").getAsString());
            System.out.println("- Biography: " + artist.get("strBiographyEN").getAsString().substring(0, 300) + "...");

            return artist.get("idArtist").getAsString();

        } catch (Exception e) {
            System.out.println("Error fetching band info: " + e.getMessage());
            return null;
        }
    }

    // Fetch albums using artistId
    public static void getAlbums(String artistId) {
        try {
            String urlString = "https://www.theaudiodb.com/api/v1/json/" + API_KEY + "/album.php?i=" + artistId;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray albums = json.getAsJsonArray("album");

            System.out.println("\nDiscography?");
            for (int i = 0; i < albums.size(); i++) {
                JsonObject album = albums.get(i).getAsJsonObject();
                System.out.println("Album " + album.get("strAlbum").getAsString() +
                        " (" + album.get("intYearReleased").getAsString() + ")");
            }

        } catch (Exception e) {
            System.out.println("Error fetching albums: " + e.getMessage());
        }
    }
}
