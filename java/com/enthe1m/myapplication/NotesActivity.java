package com.enthe1m.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class NotesActivity extends AppCompatActivity {
    private EditText notesEditText;
    private Button saveButton;
    private UserDatabase db;
    private int bookId;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notesEditText = findViewById(R.id.notes);
        saveButton = findViewById(R.id.save);

        db = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "user-database")
                .addMigrations(UserDatabase.MIGRATION_1_2, UserDatabase.MIGRATION_2_3)
                .allowMainThreadQueries()
                .build();

        bookId = getIntent().getIntExtra("BOOK_ID", -1);
        book = db.bookDao().getBookById(bookId);

        if (book == null) {
            Toast.makeText(this, "Invalid book", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        notesEditText.setText(book.notes);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notes = notesEditText.getText().toString();
                book.notes = notes;

                db.bookDao().update(book);
                Toast.makeText(NotesActivity.this, "Notes saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
