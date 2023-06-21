package com.example.whatsappdesign;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;
import java.util.List;

public class UsersRepository {
    private UserListData userListData;
    private UsersAPI api;

    public UsersRepository(){
        userListData = new UserListData();
        api = new UsersAPI(userListData);
    }

    class UserListData extends MutableLiveData<List<User>> {
        public UserListData() {
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            api.get();
        }
    }

    public LiveData<List<User>> getAll(){
        return userListData;
    }

    public void add(String username,Context context){
        api.add(username, context);
    }
}
