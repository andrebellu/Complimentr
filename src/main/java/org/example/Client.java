package org.example;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static spark.Spark.*;

public class Client {
    public static void main(String[] args) {
        port(8888);

        var client = new Client();

        get("/", (req, res) -> "<html><body style=\"background-color: black; color: white; font-size: 50px; text-align: center; vertical-align: middle; height: 100%;\">" + client.call("https://complimentr.com/api").getString("compliment") + "</body></html>");
    }

    private JSONObject call(String s) {
        // make a GET request to the URL httpClient
        var httpClient = HttpClient.newHttpClient();

        // create a request
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(s))
                .build();

        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}