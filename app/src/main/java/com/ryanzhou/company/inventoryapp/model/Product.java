package com.ryanzhou.company.inventoryapp.model;

/**
 * Created by ryanzhou on 6/27/16.
 */
public class Product {

    private int numDollars;
    private int numCents;
    private int quantity;
    private String name;
    public Product(int numDollars, int numCents, int quantity, String name){
        this.setName(name);
        this.setNumCents(numCents);
        this.setNumDollars(numDollars);
        this.setQuantity(quantity);
    }

    public int getNumDollars() {
        return numDollars;
    }

    public void setNumDollars(int numDollars) {
        this.numDollars = numDollars;
    }

    public int getNumCents() {
        return numCents;
    }

    public void setNumCents(int numCents) {
        this.numCents = numCents;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
