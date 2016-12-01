# jorttconnector

Create invoices in Jortt (www.jortt.nl) with the Jortt API connector.

###Usage
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

####Maven artifact

```
    <dependency>
      <groupId>com.getehour.connector.jortt</groupId>
      <artifactId>jorttconnector</artifactId>
      <version>1.0</version>
    </dependency>
```

Thies Edeling
https://getehour.com/