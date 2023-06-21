package com.example.whatsappdesign;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageChatDao {

    @Query("SELECT * FROM messageChat")
    List<Message> index();

    @Query("SELECT * FROM messageChat WHERE chatId = :id")
    Message get(int id);

    @Insert
    void insert(Message... message);

    @Update
    void update(Message... message);

    @Query("DELETE FROM messageChat")
    void deleteAll();

}