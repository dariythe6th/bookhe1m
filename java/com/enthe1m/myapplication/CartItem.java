package com.enthe1m.myapplication;

public class CartItem {
    private BookShop book;
    private int quantity;

    public CartItem(BookShop book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public BookShop getBook() { return book; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getTotalPrice() { return book.getPrice() * quantity; }
}