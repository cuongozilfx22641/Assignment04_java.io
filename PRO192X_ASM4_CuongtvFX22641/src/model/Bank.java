package model;

import java.util.UUID;

public class Bank {
    private final String id;
    //protected List<Customer> customers;

    public Bank() {
        //this.customers = new ArrayList<>();
        this.id = String.valueOf(UUID.randomUUID());
    }

}
