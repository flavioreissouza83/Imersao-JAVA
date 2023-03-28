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
            System.out.println("\u001b[36m" + movie.get("title") + "\u001b[0m");
            System.out.println(movie.get("image"));
            System.out.println("\u001b[30m \u001b[41m Classificação " + movie.get("imDbRating") + "\u001b[0m");
            printRating(movie.get("imDbRating"));
            System.out.println();
        }
    }
    public static void printRating(String rating) {
        int nota = (int) Math.round(Double.parseDouble(rating));
        String cor = "\u001b[33m"; // código ANSI para a cor amarela
        String reset = "\u001b[0m"; // código ANSI para resetar a cor

        for (int i = 0; i < nota; i++) {
            System.out.print(cor + "\uD83C\uDF1F" + reset);
        }
        System.out.println();
    }


}