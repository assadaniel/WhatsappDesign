package com.example.whatsappdesign;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageChatDao {

    @Query("SELECT * FROM messageChat")
    List<MessageChat> index();

    @Query("SELECT * FROM messageChat WHERE chatId = :id")
    MessageChat get(int id);

    @Insert
    void insert(MessageChat... messageChats);

    @Update
    void update(MessageChat... messageChats);

    @Query("DELETE FROM messageChat")
    void deleteAll();

}