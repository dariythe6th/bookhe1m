package com.enthe1m.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class BookShopAdapter extends RecyclerView.Adapter<BookShopAdapter.BookShopViewHolder> {

    private List<BookShop> books;
    private OnBookShopClickListener listener;

    public interface OnBookShopClickListener {
        void onBookShopClick(BookShop book);
    }

    public BookShopAdapter(List<BookShop> books, OnBookShopClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_book, parent, false);
        return new BookShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookShopViewHolder holder, int position) {
        BookShop book = books.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class BookShopViewHolder extends RecyclerView.ViewHolder {
        //private ImageView bookImage;
        private TextView bookTitle;
        private TextView bookAuthor;
        private TextView bookPrice;

        public BookShopViewHolder(@NonNull View itemView) {
            super(itemView);
           // bookImage = itemView.findViewById(R.id.book_image);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookPrice = itemView.findViewById(R.id.book_price);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onBookShopClick(books.get(position));
                }
            });
        }

        public void bind(BookShop book) {
            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthor());
            bookPrice.setText(String.format("$%.2f", book.getPrice()));
        }
    }
}