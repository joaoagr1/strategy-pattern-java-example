package com.twygo.usacucar.Regra;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.twygo.usacucar.Repositorios.AcessoRepository;
import com.twygo.usacucar.Entidades.Acesso;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TokenRegra {

    @Setter
    private String token;

    @Getter
    @Setter
    private long expiresIn;

    @Autowired
    private AcessoRepository acessoRepository;

    @Transactional
    public void refreshAccessToken() throws IOException {
        Acesso acesso = findAcessoDetails();
        if (acesso == null) {
            throw new RuntimeException("Acesso details not found in the database");
        }

        HttpURLConnection connection = createConnection("https://usacucar.twygoead.com/oauth/token");
        sendRequest(connection, acesso.getUsernameTwygo(), acesso.getPasswordTwygo());
        handleResponse(connection);
        connection.disconnect();
    }

    private Acesso findAcessoDetails() {
        List<Acesso> acessos = acessoRepository.findAll();
        if (acessos.isEmpty()) {
            throw new RuntimeException("No Acesso details found in the database");
        }
        return acessos.get(0);
    }

    private HttpURLConnection createConnection(String url) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        return connection;
    }

    private void sendRequest(HttpURLConnection connection, String username, String password) throws IOException {
        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("grant_type", "password");
        requestBodyMap.put("username", username);
        requestBodyMap.put("password", password);

        Gson gson = new Gson();
        String requestBody = gson.toJson(requestBodyMap);

        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            outputStream.writeBytes(requestBody);
            outputStream.flush();
        }
    }

    private void handleResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to refresh token, HTTP response code: " + responseCode);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
        }

        Gson gson = new Gson();
        Map<String, Object> responseMap = gson.fromJson(response.toString(), new TypeReference<Map<String, Object>>() {}.getType());

        if (responseMap != null) {
            token = (String) responseMap.get("access_token");
           // expiresIn = ((Number) responseMap.get("expires_in")).longValue();
        } else {
            throw new IOException("Invalid response from token endpoint");
        }
    }

    public String getAccessToken() throws IOException {
        if (token == null || expiresIn <= 0) {
            refreshAccessToken();
        }
        return token;
    }
}
