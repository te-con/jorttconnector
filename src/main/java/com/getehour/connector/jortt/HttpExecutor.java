package com.getehour.connector.jortt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiFunction;
import java.util.function.Function;

public class HttpExecutor {
    private JorttApiConfig apiConfig;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public HttpExecutor(JorttApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    <T> T post(String endpoint, Object data, BiFunction<Integer, InputStream, T> consume) throws IOException {
        String invoiceJson = MAPPER.writeValueAsString(data);

        StringEntity input = new StringEntity(invoiceJson);
        input.setContentEncoding("application/json");

        HttpPost post = new HttpPost(apiConfig.getBaseUrl() + endpoint);
        post.setEntity(input);

        try (CloseableHttpClient httpclient = createHttpClient();
             CloseableHttpResponse response = httpclient.execute(post)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            return consume.apply(statusCode, entity.getContent());
        }
    }

    <T> T get(String endpoint, Function<Integer, T> consume) throws IOException {
        HttpGet request = new HttpGet(apiConfig.getBaseUrl() + endpoint);

        try (CloseableHttpClient httpclient = createHttpClient();
             CloseableHttpResponse response = httpclient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            return consume.apply(statusCode);
        }
    }

    private CloseableHttpClient createHttpClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(apiConfig.getUsername(), apiConfig.getApiKey()));

        return HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
    }
}
