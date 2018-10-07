package com.getehour.connector.jortt;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("customer")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JorttCustomer {
    private String customerId;
    @JsonProperty("finance_company_name")
    private String companyName;

    @JsonProperty("finance_address_street")
    private String addressStreet;

    @JsonProperty("finance_address_city")
    private String addressCity;

    @JsonProperty("finance_address_postal_code")
    private String addressPostalCode;

    @JsonProperty("finance_address_country_code")
    private String addressCountryCode;

    @JsonProperty("finance_address_country_name")
    private String addressCountryName;

    @JsonProperty("finance_attn")
    private String addressHeading;

    @JsonProperty("finance_email")
    private String addressEmail;

    @JsonProperty("finance_extra_information")
    private String financeExtraInfo;

    private String vatNumber;
    private Integer paymentTerm;
    private String invoiceLanguage;

    private Boolean deleted;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressCountryCode() {
        return addressCountryCode;
    }

    public void setAddressCountryCode(String addressCountryCode) {
        this.addressCountryCode = addressCountryCode;
    }

    public String getAddressCountryName() {
        return addressCountryName;
    }

    public void setAddressCountryName(String addressCountryName) {
        this.addressCountryName = addressCountryName;
    }

    public String getAddressHeading() {
        return addressHeading;
    }

    public void setAddressHeading(String addressHeading) {
        this.addressHeading = addressHeading;
    }

    public String getAddressEmail() {
        return addressEmail;
    }

    public void setAddressEmail(String addressEmail) {
        this.addressEmail = addressEmail;
    }

    public String getFinanceExtraInfo() {
        return financeExtraInfo;
    }

    public void setFinanceExtraInfo(String financeExtraInfo) {
        this.financeExtraInfo = financeExtraInfo;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public Integer getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(Integer paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getInvoiceLanguage() {
        return invoiceLanguage;
    }

    public void setInvoiceLanguage(String invoiceLanguage) {
        this.invoiceLanguage = invoiceLanguage;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "JorttCustomer{" +
                "customerId='" + customerId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}