package com.getehour.connector.jortt;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JorttInvoiceLineItem that = (JorttInvoiceLineItem) o;
        return Objects.equals(vat, that.vat) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vat, amount, quantity, description);
    }
}
