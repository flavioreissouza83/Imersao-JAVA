package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // Fazer uma conexão HTTP e buscar os tops 250 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        URI adress = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(adress).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Extrair só os dados que interessam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> movieList = parser.parse(body);


        // exibir e manipular dados
        for (Map<String, String> movie : movieList) {
            System.out.println("\u001b[36m Título: " + movie.get("title") + "\u001b[m");
            System.out.println("\u001b[36m Poster: " + movie.get("image") + "\u001b[m");
            System.out.println("\u001b[30m \u001b[41m Classificação " + movie.get("imDbRating") + "\u001b[m");
            double rate = Math.round(Double.parseDouble(movie.get("imDbRating")));
            if (rate <= 7) {
                System.out.print("\uD83D\uDC4E"); // emoji polegar para baixo
            } else {
                int numberStar = (int) rate;
                for (int n = 1; n <= numberStar; n++) {
                    System.out.print("\uD83C\uDF1F"); // emoji estrela
                }
            }
            System.out.println("\n");
        }
    }
}