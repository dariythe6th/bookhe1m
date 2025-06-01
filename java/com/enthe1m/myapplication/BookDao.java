package com.enthe1m.myapplication;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Query("SELECT * FROM Book WHERE userId = :userId")
    List<Book> getAllBooksByUser(int userId);

    @Query("SELECT * FROM Book WHERE id = :bookId")
    Book getBookById(int bookId);
}
