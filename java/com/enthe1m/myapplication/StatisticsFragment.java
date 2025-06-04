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
                15.99, "https://sun9-49.userapi.com/impg/Qrnt2WclvTsDMTi9wzhdQBzoSmVgk9ffOAE-OQ/pBFijxa-hqU.jpg?size=410x648&quality=95&sign=5b22e7ab05281848f212efebb6902984&type=album"));
        books.add(new BookShop("2", "Преступление и наказание", "Фёдор Достоевский",
                "Роман о бывшем студенте Родионе Раскольникове, который совершает убийство.",
                12.50, "https://content.img-gorod.ru/pim/products/images/fc/63/018f61a9-c9e1-74a4-a62d-fd3244a1fc63.jpg"));
        books.add(new BookShop("3", "Станция Одиннадцать", "Эмили Сент-Джон Мандел",
                "Постапокалиптический роман, исследующий темы искусства, памяти и выживания в мире, изменившемся после пандемии.",
                13.75, "https://i0.wp.com/zapyataya.eu/wp-content/uploads/2024/10/stancziya-odinnadczat.jpg?fit=820%2C1312&ssl=1"));
        books.add(new BookShop("4", "Смерть – дело одинокое", "Рэй Брэдбери",
                "Мистический детектив, погружающий в атмосферу карнавала и таинственных исчезновений.",
                11.99, "https://i0.wp.com/zapyataya.eu/wp-content/uploads/2024/10/smert-delo-odinokoe.jpg?fit=820%2C1312&ssl=1"));
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