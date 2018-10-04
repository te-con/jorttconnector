package com.getehour.connector.jortt;

import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SuppressWarnings({"unchecked", "OptionalGetWithoutIsPresent"})
public class JorttConnectorTest {

    private static final JorttCredentials JORTT_CREDENTIALS = new JorttCredentials("test", "api");
    @Rule
    public MockitoRule mockitoInitializerRule = MockitoJUnit.rule();

    @Mock
    private HttpExecutor httpExecutor;

    @InjectMocks
    private JorttConnector connector;

    @Test
    public void should_not_validate_token_when_response_is_not_200() throws IOException, ApiException {
        ArgumentCaptor<ThrowingBiFunction<Integer, InputStream, Boolean>> lambdaCapture = ArgumentCaptor.forClass(ThrowingBiFunction.class);

        when(httpExecutor.get(eq("/customers/all"), eq(JORTT_CREDENTIALS), lambdaCapture.capture())).thenReturn(false);

        assertFalse(connector.validateApiKey(JORTT_CREDENTIALS));

        ThrowingBiFunction<Integer, InputStream, Boolean> value = lambdaCapture.getValue();
        assertFalse(value.apply(HttpStatus.SC_BAD_REQUEST, null));
    }

    @Test
    public void should_create_invoice() throws IOException, ApiException {
        ArgumentCaptor<ThrowingBiFunction<Integer, InputStream, Optional<String>>> lambdaCapture = ArgumentCaptor.forClass(ThrowingBiFunction.class);

        when(httpExecutor.post(eq("/invoices"), any(), eq(JORTT_CREDENTIALS), lambdaCapture.capture())).thenReturn(Optional.of("invoice-id"));

        assertEquals("invoice-id", connector.createInvoice(new JorttInvoice(new ArrayList<>()), JORTT_CREDENTIALS).get());

        ByteArrayInputStream u = new ByteArrayInputStream("{\"invoice_id\":\"invoice-id\"}".getBytes());
        assertEquals("invoice-id", (lambdaCapture.getValue().apply(HttpStatus.SC_CREATED, u)).get());
    }

    @Test
    public void should_get_all_customers() throws IOException, ApiException {
        ArgumentCaptor<ThrowingBiFunction<Integer, InputStream, PaginatedCustomerList>> lambdaCapture = ArgumentCaptor.forClass(ThrowingBiFunction.class);

        when(httpExecutor.get(eq("/customers/all?page=1&per_page=50"), eq(JORTT_CREDENTIALS), lambdaCapture.capture())).thenReturn(PaginatedCustomerListTestBuilder.start().build());
        when(httpExecutor.get(eq("/customers/all?page=2&per_page=50"), eq(JORTT_CREDENTIALS), lambdaCapture.capture())).thenReturn(PaginatedCustomerListTestBuilder.start().withPage(2).build());

        List<JorttCustomer> customers = connector.findCustomers(JORTT_CREDENTIALS);
        assertEquals(2, customers.size());
    }
}