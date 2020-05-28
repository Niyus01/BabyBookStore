package com.suyin.books_store;

public class ListUser {

    private final String title;
    private final int count;
    private final double price;


    public ListUser(String title, int count, double price) {
        this.title = title;
        this.count = count;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return count;
    }

    public double getPrice(){return price;}



}


