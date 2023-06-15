package com.example.whatsappdesign;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MessagesViewModel extends ViewModel {

    private MessagesRepository repository;
    private LiveData<List<Message>> messages;
    private int id;

    public MessagesViewModel() {

    }

    public void init(int id){
        repository = new MessagesRepository(id);
        messages = repository.getAll();
    }

    public LiveData<List<Message>> get() {return messages;}

    public void add(MessageToSend message) {
        repository.add(message);
    }
}
