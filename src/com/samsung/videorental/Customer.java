package com.samsung.videorental;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
    private String name;

    private List<Rental> rentals = new ArrayList<>();

    public Customer(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);

    }

    public String getReport() {
        String result = "Customer Report for " + getName() + "\n";

        List<Rental> rentals = getRentals();

        double totalCharge = 0;
        int totalPoint = 0;

        for (Rental each : rentals) {
            result += each.getRentalReport();
            totalCharge += each.getCharge();
            totalPoint += each.getPoint();
        }

        result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";

        if (totalPoint >= 10) {
            System.out.println("Congrat! You earned one free coupon");
        }
        if (totalPoint >= 30) {
            System.out.println("Congrat! You earned two free coupon");
        }
        return result;
    }
}
