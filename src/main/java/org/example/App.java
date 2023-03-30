package org.example;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // Fazer uma conexão HTTP e buscar os tops 250 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI adress = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(adress).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Extrair só os dados que interessam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> movieList = parser.parse(body);


        // exibir e manipular dados
        var generator = new StickerGenerator();
        for (Map<String, String> movie : movieList) {

            String urlImage = movie.get("image");
            String title = movie.get("title");
            double rate = Double.parseDouble(movie.get("imDbRating"));

            int roundedRate = (int) Math.round(rate);
            String stars = "⭐️".repeat(roundedRate);

            String textStickers;
            if (rate >= 8.0) {
                textStickers = "TOPZERA";
            } else {
                textStickers = "BEM MALEMÁ";
            }

            InputStream inputStream = new URL(urlImage).openStream();
            String nameFile = title + ".png";

            generator.creator(inputStream, nameFile, textStickers, stars);

            System.out.println(title);
            System.out.println("Classificação ImDb: " + roundedRate + "\n" + stars);
            System.out.println();
        }
    }
}