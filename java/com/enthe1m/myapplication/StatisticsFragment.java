package com.enthe1m.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment implements BookShopAdapter.OnBookShopClickListener {

    private NavigationManager navigationManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof NavigationManager) {
            navigationManager = (NavigationManager) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.books_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<BookShop> books = new ArrayList<>();
        books.add(new BookShop("1", "Война и мир", "Лев Толстой",
                "Роман-эпопея, описывающий русское общество в эпоху войн против Наполеона.",
                15.99, "https://example.com/war_and_peace.jpg"));
        books.add(new BookShop("2", "Преступление и наказание", "Фёдор Достоевский",
                "Роман о бывшем студенте Родионе Раскольникове, который совершает убийство.",
                12.50, "https://example.com/crime_and_punishment.jpg"));

        BookShopAdapter adapter = new BookShopAdapter(books, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onBookShopClick(BookShop book) {
        BookDetailFragment fragment = BookDetailFragment.newInstance(book);
        navigationManager.navigateToFragment(fragment, true);
    }
}