package com.getehour.connector.jortt;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class JorttConnectorTest {

    @Rule
    public MockitoRule mockitoInitializerRule = MockitoJUnit.rule();

    @Mock
    private HttpExecutor httpExecutor;
    private JorttConnector connector;

    @Before
    public void setUp() throws Exception {
        connector = new JorttConnector(httpExecutor);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void should_be_ok_when_response_is_200() throws IOException {
        ArgumentCaptor<Function<Integer, Boolean>> lambdaCapture = lambdaCapture();
        when(httpExecutor.get(eq("/customers"), eq("u"), eq("api"), lambdaCapture.capture())).thenReturn(true);

        assertTrue(connector.validateApiKey("u", "api"));

        assertTrue(lambdaCapture.getValue().apply(HttpStatus.SC_OK));
    }

    @Test
    public void should_not_be_ok_when_response_is_not_200() throws IOException {
        ArgumentCaptor<Function<Integer, Boolean>> lambdaCapture = lambdaCapture();
        when(httpExecutor.get(eq("/customers"), eq("u"), eq("api"), lambdaCapture.capture())).thenReturn(false);

        assertFalse(connector.validateApiKey("u", "api"));

        assertFalse(lambdaCapture.getValue().apply(HttpStatus.SC_BAD_REQUEST));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void should_create_invoice() throws IOException {
        ArgumentCaptor<BiFunction<Integer, InputStream, Optional<String>>> lambdaCapture = ArgumentCaptor.forClass(BiFunction.class);
        when(httpExecutor.post(eq("/invoices"), any(), anyString(), anyString(), lambdaCapture.capture())).thenReturn(Optional.of("invoice-id"));

        assertEquals("invoice-id", connector.createInvoice(new JorttInvoice(new ArrayList<>()), "u", "api").get());

        ByteArrayInputStream u = new ByteArrayInputStream("{\"invoice_id\":\"invoice-id\"}".getBytes());
        assertEquals("invoice-id", (lambdaCapture.getValue().apply(HttpStatus.SC_CREATED, u)).get());
    }


    @SuppressWarnings("unchecked")
    private ArgumentCaptor<Function<Integer, Boolean>> lambdaCapture() {
        return ArgumentCaptor.forClass(Function.class);
    }
}