package com.suyin.books_store;

public class Book {
    private final String title;
    private final String author;
    private final int imageResource;
    private final String info;
    private final double price;

    public Book(String title, String author, double price, int imageResource, String info) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.imageResource = imageResource;
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice(){
        return price;}

    public int getImageResource() {
        return imageResource;
    }

    public String getInfo(){
        return info;}



}
