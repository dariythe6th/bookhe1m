package com.enthe1m.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar; // Добавьте этот импорт
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView titleText;
        public TextView authorText;
        public TextView yearText;
        public TextView genreText;
        public TextView ratingText;
        public ImageButton deleteButton;
        public ImageButton editButton;

        public BookViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.title_text);
            authorText = itemView.findViewById(R.id.author_text);
            yearText = itemView.findViewById(R.id.year_text);
            genreText = itemView.findViewById(R.id.genre_text);
            ratingText = itemView.findViewById(R.id.rating_text);
            deleteButton = itemView.findViewById(R.id.delete_button);
            editButton = itemView.findViewById(R.id.edit_button);
        }
    }

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.titleText.setText(book.getTitle());
        holder.authorText.setText(book.getAuthor());
        holder.yearText.setText(String.valueOf(book.getYear()));
        holder.genreText.setText(book.getGenre());
        holder.ratingText.setText(String.format("%.1f", book.getRating()));

        // Обработчик удаления книги
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Удаление книги")
                        .setMessage("Вы уверены, что хотите удалить эту книгу?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseHelper dbHelper = new DatabaseHelper(v.getContext());
                                dbHelper.deleteBook(book);
                                bookList.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(v.getContext(), "Книга удалена", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Нет", null)
                        .show();
            }
        });

        // Обработчик редактирования книги
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(v.getContext(), book, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


    private void showEditDialog(Context context, final Book book, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Редактировать книгу");

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_book, null);
        builder.setView(view);

        final EditText titleInput = view.findViewById(R.id.title_input);
        final EditText authorInput = view.findViewById(R.id.author_input);
        final EditText yearInput = view.findViewById(R.id.year_input);
        final EditText genreInput = view.findViewById(R.id.genre_input);
        final RatingBar ratingBar = view.findViewById(R.id.rating_bar);

        titleInput.setText(book.getTitle());
        authorInput.setText(book.getAuthor());
        yearInput.setText(String.valueOf(book.getYear()));
        genreInput.setText(book.getGenre());
        ratingBar.setRating(book.getRating());

        builder.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    book.setTitle(titleInput.getText().toString());
                    book.setAuthor(authorInput.getText().toString());
                    book.setYear(Integer.parseInt(yearInput.getText().toString()));
                    book.setGenre(genreInput.getText().toString());
                    book.setRating(ratingBar.getRating());

                    DatabaseHelper dbHelper = new DatabaseHelper(context);
                    dbHelper.updateBook(book);

                    notifyItemChanged(position);
                    Toast.makeText(context, "Изменения сохранены", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(context, "Проверьте правильность ввода чисел (Год издания)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Отмена", null);
        builder.show();
    }
}