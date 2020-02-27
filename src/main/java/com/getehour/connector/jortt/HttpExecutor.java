package com.getehour.connector.jortt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;

public class HttpExecutor {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private String jorttBaseUrl;

    public HttpExecutor(String jorttBaseUrl) {
        this.jorttBaseUrl = jorttBaseUrl;
    }

    <T> T post(String endpoint, Object data, JorttCredentials credentials, ThrowingBiFunction<Integer, InputStream, T> consume) throws IOException, ApiException {
        String json = MAPPER.writeValueAsString(data);

        StringEntity input = new StringEntity(json, "UTF-8");
        input.setContentType("application/json");

        HttpPost post = new HttpPost(jorttBaseUrl + endpoint);
        post.setEntity(input);

        try (CloseableHttpClient httpclient = createHttpClient(credentials.getUsername(), credentials.getApiKey());
             CloseableHttpResponse response = httpclient.execute(post)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            return consume.apply(statusCode, entity.getContent());
        }
    }

    <T> T get(String endpoint, JorttCredentials credentials, ThrowingBiFunction<Integer, InputStream, T> consume) throws ApiException, IOException {
        HttpGet request = new HttpGet(jorttBaseUrl + endpoint);

        try (CloseableHttpClient httpClient = createHttpClient(credentials.getUsername(), credentials.getApiKey());
             CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            return consume.apply(statusCode, entity.getContent());
        }
    }

    private CloseableHttpClient createHttpClient(String username, String apiKey) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, apiKey));

        return HttpClients
                .custom()
                .setDefaultCredentialsProvider(credentialsProvider)
                .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
     }
}
