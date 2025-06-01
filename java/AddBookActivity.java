package com.enthe1m.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Calendar;

public class AddBookActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText authorEditText;
    private Button addButton;
    private UserDatabase db;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        titleEditText = findViewById(R.id.title);
        authorEditText = findViewById(R.id.author);
        addButton = findViewById(R.id.add);

        db = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "user-database")
                .allowMainThreadQueries()
                .build();

        userId = getIntent().getIntExtra("USER_ID", -1);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String author = authorEditText.getText().toString();

                if (title.isEmpty() || author.isEmpty()) {
                    Toast.makeText(AddBookActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Book book = new Book();
                book.setTitle(title);
                book.setAuthor(author);
                book.userId = userId;
                //book.timestamp = Calendar.getInstance().getTimeInMillis();

                db.bookDao().insert(book);
                Toast.makeText(AddBookActivity.this, "Book added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
