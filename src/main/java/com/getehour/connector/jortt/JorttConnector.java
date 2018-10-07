package com.getehour.connector.jortt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@SuppressWarnings("WeakerAccess")
public class JorttConnector {
    private static final Logger LOGGER = LoggerFactory.getLogger(JorttConnector.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

    }
    private static final TypeReference<HashMap<String, Object>> TYPE_REF = new TypeReference<HashMap<String, Object>>() {
    };

    private final HttpExecutor httpExecutor;

    public JorttConnector(HttpExecutor httpExecutor) {
        this.httpExecutor = httpExecutor;
    }

    public boolean validateApiKey(JorttCredentials credentials) throws IOException, ApiException {
        return httpExecutor.get("/customers/all", credentials, (statusCode, stream) -> statusCode == HttpStatus.SC_OK);
    }

    public Optional<String> createInvoice(JorttInvoice invoice, JorttCredentials credentials) throws ApiException, IOException {
        return httpExecutor.post("/invoices", invoice, credentials, this::parseInvoiceCreationResponse);
    }

    private Optional<String> parseInvoiceCreationResponse(Integer statusCode, InputStream inputStream) throws ApiException {
        boolean okResponse = statusCode == HttpStatus.SC_CREATED;

        try {
            Map<String, Object> o = MAPPER.readValue(inputStream, TYPE_REF);

            if (okResponse) {
                Object invoiceId = o.get("invoice_id");
                LOGGER.info("Creating invoice in Jortt API: {}", invoiceId);

                return Optional.of((String) invoiceId);
            } else {
                throw new ApiException(statusCode, "Invalid response from API");
            }
        } catch (IOException e) {
            throw new ApiException("Failed to parse response", e);
        }
    }

    public List<JorttCustomer> findCustomers(JorttCredentials credentials) throws ApiException, IOException {
        int page = 1;
        int pages;

        List<JorttCustomer> customers = new ArrayList<>();

        do {
            String endpoint = String.format("/customers/all?page=%d&per_page=50", page);
            PaginatedCustomerList paginatedCustomerList = httpExecutor.get(endpoint, credentials, this::parsePaginatedCustomerList);

            customers.addAll(paginatedCustomerList.getCustomers());

            page = paginatedCustomerList.getPage() + 1;
            pages = paginatedCustomerList.getTotalPages();
        } while (page <= pages);

        return customers;
    }

    private PaginatedCustomerList parsePaginatedCustomerList(Integer statusCode, InputStream inputStream) throws ApiException {
        boolean okResponse = statusCode == HttpStatus.SC_OK;

        if (okResponse) {
            try {
                return MAPPER.readValue(inputStream, PaginatedCustomerList.class);
            } catch (IOException e) {
                throw new ApiException("Failed to parse response", e);
            }
        } else {
            throw new ApiException(statusCode, "Invalid response from API: " + statusCode);
        }
    }
}