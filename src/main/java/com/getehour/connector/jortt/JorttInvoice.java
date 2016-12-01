package com.getehour.connector.jortt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("invoice")
public class JorttInvoice {
    @JsonProperty("customer_id")
    private String customerId;

    private String reference;

    @JsonProperty("line_items")
    private List<JorttInvoiceLineItem> lineItems;

    public JorttInvoice(String customerId, List<JorttInvoiceLineItem> lineItems) {
        this.customerId = customerId;
        this.lineItems = lineItems;
    }

    public JorttInvoice(int i, List<JorttInvoiceLineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getReference() {
        return reference;
    }

    public List<JorttInvoiceLineItem> getLineItems() {
        return lineItems;
    }
}
