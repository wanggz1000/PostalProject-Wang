package com.fh.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PostController {

    private static final String BASE_URI = "http://localhost:8080/api/";

    @FXML
    private TextField nameFieldLetter;
    @FXML
    private TextField countryField;
    @FXML
    private TextArea statusLabel;
    @FXML
    private TextField nameFieldPackage;
    @FXML
    private TextField weightField;

    @FXML
    private void sendLetter() {
        String json = createJson("name", nameFieldLetter.getText(), "country", countryField.getText());
        try {
            sendPostRequest("letters", json);
        } catch (Exception e) {
            statusLabel.setText("Failed to send letter: " + e.getMessage());
        }
    }

    @FXML
    private void refreshStatus() {
        try {
            // Clear the previous status before refreshing
            statusLabel.clear();
            String response = sendGetRequest("status");
            statusLabel.setText(response);
        } catch (Exception e) {
            statusLabel.setText("Failed to refresh status: " + e.getMessage());
        }
    }

    @FXML
    private void sendPackage() {
        String json = createJson("name", nameFieldPackage.getText(), "weight", weightField.getText());
        try {
            sendPostRequest("packages", json);
        } catch (Exception e) {
            statusLabel.setText("Failed to send package: " + e.getMessage());
        }
    }

    private String createJson(String... keyValuePairs) {
        StringBuilder json = new StringBuilder("{");
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            json.append("\"").append(keyValuePairs[i]).append("\":\"")
                    .append(keyValuePairs[i + 1]).append("\"");
            if (i < keyValuePairs.length - 2) {
                json.append(",");
            }
        }
        json.append("}");
        return json.toString();
    }

    private void sendPostRequest(String endpoint, String json) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URI + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String sendGetRequest(String endpoint) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URI + endpoint))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}