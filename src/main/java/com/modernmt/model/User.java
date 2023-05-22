package com.modernmt.model;

public class User extends Model {

    private long id;
    private String name;
    private String email;
    private String registrationDate;
    private String country;
    private String isBusiness;
    private String status;
    private BillingPeriod billingPeriod;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getCountry() {
        return country;
    }

    public String getIsBusiness() {
        return isBusiness;
    }

    public String getStatus() {
        return status;
    }

    public BillingPeriod getBillingPeriod() {
        return billingPeriod;
    }
}

