# Deprecated

The connector uses Jortt's old API key authentication which is no longer supported.

# jorttconnector

Get customers from and create invoices in Jortt (www.jortt.nl) with the Jortt API connector.

### Usage

#### To validate an API key:
```        
        HttpExecutor executor = new HttpExecutor("https://app.jortt.nl/api");
        JorttConnector jorttConnector = new JorttConnector(executor);
        boolean valid = jorttConnector.validateApiKey(new JorttCredentials("<your username>", "<your apikey>"));
```

#### To get all customers:
```        
        HttpExecutor executor = new HttpExecutor("https://app.jortt.nl/api");
        JorttConnector jorttConnector = new JorttConnector(executor);

        List<JorttCustomer> customers= jorttConnector.findCustomers(new JorttCredentials("<your username>", "<your apikey>"));
```

#### To create an invoice:

```        
        HttpExecutor executor = new HttpExecutor("https://app.jortt.nl/api");
        JorttConnector jorttConnector = new JorttConnector(executor);

        JorttInvoiceLineItem lineItem = new JorttInvoiceLineItem(new BigDecimal("21"), new BigDecimal("5"), new BigDecimal("10"), "Another line item");
        List<JorttInvoiceLineItem> lineItems = new ArrayList<>();
        lineItems.add(lineItem);
        Optional<String> invoiceId = jorttConnector.createInvoice(new JorttInvoice(lineItems), new JorttCredentials("<your username>", "<your apikey>"));
```

For more information on the API, check https://www.jortt.nl/koppelingen/api/

Thies Edeling
https://getehour.com/
