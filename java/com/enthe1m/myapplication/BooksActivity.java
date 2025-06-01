package com.enthe1m.myapplication;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BooksActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        // Остальной код остается без изменений
        if (dbHelper.getBooksCount() == 0) {
            addSampleBooks();
        }

        refreshBookList();

        Button addButton = findViewById(R.id.add_button);
        Button searchButton = findViewById(R.id.search_button);
        EditText searchInput = findViewById(R.id.search_input);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddBookDialog();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchInput.getText().toString();
                if (!query.isEmpty()) {
                    List<Book> searchResults = dbHelper.searchBooks(query);
                    adapter = new BookAdapter(searchResults);
                    recyclerView.setAdapter(adapter);
                } else {
                    refreshBookList();
                }
            }
        });
    }

    // Остальные методы остаются без изменений
    private void addSampleBooks() {
        dbHelper.addBook(new Book(0, "Война и мир", "Лев Толстой", 1869, "Роман", 4.8f));
        dbHelper.addBook(new Book(0, "Преступление и наказание", "Фёдор Достоевский", 1866, "Роман", 4.7f));
        dbHelper.addBook(new Book(0, "1984", "Джордж Оруэлл", 1949, "Антиутопия", 4.6f));
        dbHelper.addBook(new Book(0, "Мастер и Маргарита", "Михаил Булгаков", 1967, "Роман", 4.9f));
        dbHelper.addBook(new Book(0, "Гарри Поттер и философский камень", "Дж. К. Роулинг", 1997, "Фэнтези", 4.5f));
    }

    private void refreshBookList() {
        List<Book> bookList = dbHelper.getAllBooks();
        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
    }

    private void showAddBookDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить новую книгу");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_book, null);
        builder.setView(view);

        final EditText titleInput = view.findViewById(R.id.title_input);
        final EditText authorInput = view.findViewById(R.id.author_input);
        final EditText yearInput = view.findViewById(R.id.year_input);
        final EditText genreInput = view.findViewById(R.id.genre_input);
        final EditText ratingInput = view.findViewById(R.id.rating_input);


        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String title = titleInput.getText().toString();
                    String author = authorInput.getText().toString();
                    int year = Integer.parseInt(yearInput.getText().toString());
                    String genre = genreInput.getText().toString();
                    float rating = Float.parseFloat(ratingInput.getText().toString());

                    if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                        Toast.makeText(BooksActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (rating < 0 || rating > 5) {
                        Toast.makeText(BooksActivity.this, "Рейтинг должен быть от 0 до 5", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Book newBook = new Book(0, title, author, year, genre, rating);
                    dbHelper.addBook(newBook);
                    refreshBookList();
                    Toast.makeText(BooksActivity.this, "Книга добавлена", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(BooksActivity.this, "Проверьте правильность ввода чисел", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

}
