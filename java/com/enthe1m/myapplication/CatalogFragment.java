package com.enthe1m.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar; // Import RatingBar
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CatalogFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private EditText searchInput;
    private Button addButton, searchButton;

    public CatalogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        dbHelper = new DatabaseHelper(requireContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        searchInput = view.findViewById(R.id.search_input);
        addButton = view.findViewById(R.id.add_button);
        searchButton = view.findViewById(R.id.search_button);

        if (dbHelper.getBooksCount() == 0) {
            addSampleBooks();
        }

        refreshBookList();

        addButton.setOnClickListener(v -> showAddBookDialog());

        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString();
            if (!query.isEmpty()) {
                List<Book> searchResults = dbHelper.searchBooks(query);
                adapter = new BookAdapter(searchResults);
                recyclerView.setAdapter(adapter);
            } else {
                refreshBookList();
            }
        });

        return view;
    }

    private void addSampleBooks() {
        dbHelper.addBook(new Book(0, "Война и мир", "Лев Толстой", 1869, "Роман", 4.0f));
        dbHelper.addBook(new Book(0, "Преступление и наказание", "Фёдор Достоевский", 1866, "Роман", 4.5f));
        dbHelper.addBook(new Book(0, "1984", "Джордж Оруэлл", 1949, "Антиутопия", 3.5f));
        dbHelper.addBook(new Book(0, "Мастер и Маргарита", "Михаил Булгаков", 1967, "Роман", 5.0f));
        dbHelper.addBook(new Book(0, "Гарри Поттер и философский камень", "Дж. К. Роулинг", 1997, "Фэнтези", 4.5f));
    }

    private void refreshBookList() {
        List<Book> bookList = dbHelper.getAllBooks();
        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
    }

    private void showAddBookDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Добавить новую книгу");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_book, null);
        builder.setView(view);

        final EditText titleInput = view.findViewById(R.id.title_input);
        final EditText authorInput = view.findViewById(R.id.author_input);
        final EditText yearInput = view.findViewById(R.id.year_input);
        final EditText genreInput = view.findViewById(R.id.genre_input);
        final RatingBar ratingBar = view.findViewById(R.id.rating_bar);

        builder.setPositiveButton("Добавить", (dialog, which) -> {
            try {
                String title = titleInput.getText().toString();
                String author = authorInput.getText().toString();
                int year = Integer.parseInt(yearInput.getText().toString());
                String genre = genreInput.getText().toString();
                float rating = ratingBar.getRating();

                if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                    Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                    return;
                }


                Book newBook = new Book(0, title, author, year, genre, rating);
                dbHelper.addBook(newBook);
                refreshBookList();
                Toast.makeText(requireContext(), "Книга добавлена", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "Проверьте правильность ввода чисел (Год издания)", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Отмена", null);
        builder.show();
    }
}