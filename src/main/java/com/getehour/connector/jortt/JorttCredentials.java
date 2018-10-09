package com.getehour.connector.jortt;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JorttCredentials that = (JorttCredentials) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(apiKey, that.apiKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, apiKey);
    }
}
