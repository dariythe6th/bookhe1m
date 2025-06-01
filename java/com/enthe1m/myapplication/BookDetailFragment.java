package com.enthe1m.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class BookDetailFragment extends Fragment {
    private static final String ARG_BOOK = "book";
    private BookShop book;

    public static BookDetailFragment newInstance(BookShop book) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(ARG_BOOK);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);

        ImageView bookImage = view.findViewById(R.id.book_detail_image);
        TextView bookTitle = view.findViewById(R.id.book_detail_title);
        TextView bookAuthor = view.findViewById(R.id.book_detail_author);
        TextView bookPrice = view.findViewById(R.id.book_detail_price);
        TextView bookDescription = view.findViewById(R.id.book_detail_description);
        Button addToCartButton = view.findViewById(R.id.add_to_cart_button);
        Button backButton = view.findViewById(R.id.back_button);

        //Picasso.get().load(book.getImageUrl()).into(bookImage);
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookPrice.setText(String.format("$%.2f", book.getPrice()));
        bookDescription.setText(book.getDescription());

        backButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        addToCartButton.setOnClickListener(v -> {
            CartManager.getInstance().addToCart(book, 1);
            Toast.makeText(getContext(), "Книга добавлена в корзину", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}