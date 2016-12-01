# jorttconnector
Jortt API connector for creating invoices in https://www.jortt.nl/

Usage is straight forward, to create an invoice:

```        
        JorttApiConfig config = new JorttApiConfig("https://app.jortt.nl/api", "<your username>", "<your apikey>");
        HttpExecutor executor = new HttpExecutor(config);
        JorttConnector jorttConnector = new JorttConnector(executor);
        jorttConnector.validateApiKey(config);

        JorttInvoiceLineItem lineItem = new JorttInvoiceLineItem(new BigDecimal("21"), new BigDecimal("5"), new BigDecimal("10"), "Another line item");
        List<JorttInvoiceLineItem> lineItems = new ArrayList<>();
        lineItems.add(lineItem);
        Optional<String> invoiceId = jorttConnector.createInvoice(config, new JorttInvoice(lineItems));
```

For more information on the API, check https://www.jortt.nl/koppelingen/api/

