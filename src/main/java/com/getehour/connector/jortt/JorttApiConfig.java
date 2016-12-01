package com.getehour.connector.jortt;

import java.io.Serializable;

public class JorttApiConfig implements Serializable {
    private String baseUrl;
    private String username;
    private String apiKey;

    public JorttApiConfig(String baseUrl, String username, String apiKey) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getApiKey() {
        return apiKey;
    }
}
