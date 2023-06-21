package com.example.whatsappdesign;

import static com.example.whatsappdesign.UsersActivity.currentConnectedUsername;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import com.example.whatsappdesign.MessageChatDao;

public class MessagesRepository {
    private MessageListData messageListData;
    private MessageAPI api;
    private int id;
//////////////////////////
    private MessageChatDao messageChatDao;

    public MessagesRepository(int id){
        UserDB userDB = LocalDB.userDB;
        messageChatDao = userDB.messageChatDao();

        messageListData = new MessageListData();
        api = new MessageAPI(messageListData, messageChatDao);
        this.id = id;
    }

    public void add(MessageToSend message) {
//        api.add(id,message);
        // Get the current time
        Calendar currentTime = Calendar.getInstance();

        // Extract the hour and minute values
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        // Format the hour and minute values as strings
        String formattedHour = String.format(Locale.getDefault(), "%02d", hour);
        String formattedMinute = String.format(Locale.getDefault(), "%02d", minute);

        // Construct the formatted time string
        String formattedTime = formattedHour + ":" + formattedMinute;
        Message realMessage = new Message(message.getMsg(),
                formattedTime,new OnlyUsername(currentConnectedUsername));
        List<Message> messageList1 = messageListData.getValue();
        messageList1.add(realMessage);
        messageListData.setValue(messageList1);
        api.add(id,message, messageList1);


//        // Add the new message to the local database
//        MessageChat messageChat = messageChatDao.get(id);
//        if (messageChat != null) {
//            messageChat.getListMessage().add(realMessage);
//            messageChatDao.update(messageChat);
//        }
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData(){
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
//                messageListData.postValue(messageChatDao.index());
                //List<Message> messages = messageChatDao.index();
                MessageChat messageChat = messageChatDao.get(id);
                if(messageChat!=null) {
                    messageListData.postValue(messageChat.getListMessage());
                }
            }).start();

            api.get(id);
        }
    }

    public LiveData<List<Message>> getAll() {return messageListData;}
}
