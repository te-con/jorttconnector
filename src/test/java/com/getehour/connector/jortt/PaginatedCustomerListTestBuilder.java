package com.getehour.connector.jortt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PaginatedCustomerListTestBuilder {
    private List<JorttCustomer> customers;
    private Integer page;
    private Integer totalPages;

    private PaginatedCustomerListTestBuilder() {
        customers = new ArrayList<>();
    }

    public static PaginatedCustomerListTestBuilder start() {
        return new PaginatedCustomerListTestBuilder().withPage(1).withTotalPages(2).withCustomer(new JorttCustomer());
    }

    public PaginatedCustomerListTestBuilder withCustomer(JorttCustomer... customer) {
        customers.addAll(Arrays.asList(customer));

        return this;
    }

    public PaginatedCustomerListTestBuilder withPage(Integer page) {
        this.page = page;
        return this;
    }

    public PaginatedCustomerListTestBuilder withTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public PaginatedCustomerList build() {
        PaginatedCustomerList paginatedCustomerList = new PaginatedCustomerList();
        paginatedCustomerList.setCustomers(customers);
        paginatedCustomerList.setPage(page);
        paginatedCustomerList.setTotalPages(totalPages);
        return paginatedCustomerList;
    }
}
