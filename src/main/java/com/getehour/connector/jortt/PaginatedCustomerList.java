package com.getehour.connector.jortt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatedCustomerList {
    private List<JorttCustomer> customers;
    private Integer page;
    private Integer totalPages;

    public PaginatedCustomerList() {
    }

    PaginatedCustomerList(Integer page, Integer totalPages) {
        this.page = page;
        this.totalPages = totalPages;
        this.customers = new ArrayList<>();
    }

    public List<JorttCustomer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<JorttCustomer> customers) {
        this.customers = customers;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
