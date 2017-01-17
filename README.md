# jorttconnector

Create invoices in Jortt (www.jortt.nl) with the Jortt API connector.

###Usage
Usage is straight forward, to create an invoice:

```        
        HttpExecutor executor = new HttpExecutor("https://app.jortt.nl/api");
        JorttConnector jorttConnector = new JorttConnector(executor);
        jorttConnector.validateApiKey("<your username>", "<your apikey>");

        JorttInvoiceLineItem lineItem = new JorttInvoiceLineItem(new BigDecimal("21"), new BigDecimal("5"), new BigDecimal("10"), "Another line item");
        List<JorttInvoiceLineItem> lineItems = new ArrayList<>();
        lineItems.add(lineItem);
        Optional<String> invoiceId = jorttConnector.createInvoice(new JorttInvoice(lineItems), "<your username>", "<your apikey>");
```

For more information on the API, check https://www.jortt.nl/koppelingen/api/

####Maven artifact

```
    <dependency>
      <groupId>com.getehour.connector.jortt</groupId>
      <artifactId>jorttconnector</artifactId>
      <version>1.2</version>
    </dependency>
```

Thies Edeling
https://getehour.com/