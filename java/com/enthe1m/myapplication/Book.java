package com.enthe1m.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Book {
    public int userId;
    public String notes;
    @PrimaryKey
    private int id;
    private String title;
    private String author;
    private int year;
    private String genre;
    private float rating;

    public Book(int id, String title, String author, int year, String genre, float rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }
    public Book() {}
    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
}
