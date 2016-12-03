package com.getehour.connector.jortt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;

public class JorttConnector {
    private static final Logger LOGGER = LoggerFactory.getLogger(JorttConnector.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final HttpExecutor httpExecutor;

    public JorttConnector(HttpExecutor httpExecutor) {
        this.httpExecutor = httpExecutor;
    }

    public boolean validateApiKey(String username, String apiKey) {
        try {
            return httpExecutor.get("/customers", username, apiKey, (statusCode) -> {
                boolean okResponse = statusCode == HttpStatus.SC_OK;

                if (okResponse) {
                    LOGGER.info("Succesfull authentication with Jortt API");
                } else {
                    LOGGER.warn("Got response {} on authentication request with Jortt API", statusCode);
                }
                return okResponse;
            });
        } catch (IOException e) {
            LOGGER.error("Failed to connect to Jortt API", e);
            return false;
        }
    }

    public Optional<String> createInvoice(JorttInvoice invoice, String username, String apiKey) {

        try {
            return httpExecutor.post("/invoices", invoice, username, apiKey, this::parseInvoiceCreationResponse);
        } catch (IOException e) {
            LOGGER.error("Failed to connect to Jortt API", e);
        }

        return Optional.empty();
    }

    private Optional<String> parseInvoiceCreationResponse(Integer statusCode, InputStream inputStream) {
        try {
            boolean okResponse = statusCode == HttpStatus.SC_CREATED;
            if (okResponse) {
                TypeReference<HashMap<String, Object>> typeRef
                        = new TypeReference<HashMap<String, Object>>() {
                };

                HashMap<String, Object> o = MAPPER.readValue(inputStream, typeRef);

                Object invoiceId = o.get("invoice_id");
                LOGGER.info("Creating invoice in Jortt API: {}", invoiceId);

                return Optional.of((String) invoiceId);
            } else {
                LOGGER.warn("Got failed response {} on invoice creation in Jortt", statusCode);
                return Optional.empty();
            }
        } catch (Exception e) {
            LOGGER.info("Failed to parse response after invoice creation", e);
            return Optional.empty();
        }
    }

}
