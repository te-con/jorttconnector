package com.getehour.connector.jortt;

import java.math.BigDecimal;

public class JorttInvoiceLineItem {
    private BigDecimal vat;

    private BigDecimal amount;

    private BigDecimal quantity;

    private String description;

    public JorttInvoiceLineItem(BigDecimal vat, BigDecimal amount, BigDecimal quantity, String description) {
        this.vat = vat;
        this.amount = amount;
        this.quantity = quantity;
        this.description = description;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }
}
