package com.getehour.connector.jortt;

public class JorttCredentials {
    private final String username;
    private final String apiKey;

    public JorttCredentials(String username, String apiKey) {
        this.username = username != null ? username.trim() : null;
        this.apiKey = apiKey != null ? apiKey.trim() : null;
    }

    public String getUsername() {
        return username;
    }

    public String getApiKey() {
        return apiKey;
    }
}
