package com.enthe1m.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class BookShop implements Parcelable {
    private String id;
    private String title;
    private String author;
    private String description;
    private double price;
    private String imageUrl;

    public BookShop(String id, String title, String author, String description, double price, String imageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    protected BookShop(Parcel in) {
        id = in.readString();
        title = in.readString();
        author = in.readString();
        description = in.readString();
        price = in.readDouble();
        imageUrl = in.readString();
    }

    public static final Creator<BookShop> CREATOR = new Creator<BookShop>() {
        @Override
        public BookShop createFromParcel(Parcel in) {
            return new BookShop(in);
        }

        @Override
        public BookShop[] newArray(int size) {
            return new BookShop[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(description);
        parcel.writeDouble(price);
        parcel.writeString(imageUrl);
    }

    // Геттеры
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
}
