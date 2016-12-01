package com.getehour.connector.jortt;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
        JorttApiConfig config = new JorttApiConfig("http://", "im", "invalid");

        ArgumentCaptor<Function<Integer, Boolean>> lambdaCapture = lambdaCapture();
        when(httpExecutor.execute(isA(HttpGet.class), lambdaCapture.capture())).thenReturn(true);

        assertTrue(connector.validateApiKey(config));

        assertTrue(lambdaCapture.getValue().apply(HttpStatus.SC_OK));
    }

    @Test
    public void should_not_be_ok_when_response_is_not_200() throws IOException {
        JorttApiConfig config = new JorttApiConfig("http://", "im", "invalid");

        ArgumentCaptor<Function<Integer, Boolean>> lambdaCapture = lambdaCapture();
        when(httpExecutor.execute(isA(HttpGet.class), lambdaCapture.capture())).thenReturn(false);

        assertFalse(connector.validateApiKey(config));

        assertFalse(lambdaCapture.getValue().apply(HttpStatus.SC_BAD_REQUEST));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void should_create_invoice() throws IOException {
        JorttApiConfig config = new JorttApiConfig("http://", "im", "invalid");

        ArgumentCaptor<BiFunction<Integer, InputStream, Optional<String>>> lambdaCapture = ArgumentCaptor.forClass(BiFunction.class);
        when(httpExecutor.execute(isA(HttpPost.class), lambdaCapture.capture())).thenReturn(Optional.of("invoice-id"));

        assertEquals("invoice-id", connector.createInvoice(config, new JorttInvoice(1, new ArrayList<>())).get());

        ByteArrayInputStream u = new ByteArrayInputStream("{\"invoice_id\":\"invoice-id\"}".getBytes());
        assertEquals("invoice-id", (lambdaCapture.getValue().apply(HttpStatus.SC_CREATED, u)).get());
    }



    @SuppressWarnings("unchecked")
    private ArgumentCaptor<Function<Integer, Boolean>> lambdaCapture() {
        return ArgumentCaptor.forClass(Function.class);
    }
}