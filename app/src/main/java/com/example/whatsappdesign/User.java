package com.example.whatsappdesign;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "user")
@TypeConverters({UserNoPasswordConverter.class, MessageDetailsConverter.class})
public class User {

    @PrimaryKey(autoGenerate = false)
    private int id;
   private UserNoPassword user;
   private MessageDetails lastMessage;

    public User(int id, UserNoPassword user, MessageDetails lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public User(UserDataFromAdd userDataFromAdd) {
        this.id = userDataFromAdd.getId();
        this.user = userDataFromAdd.getUser();
        this.lastMessage = null;
    }

    public int getId() {
        return id;
    }

    public UserNoPassword getUser() {
        return user;
    }

    public MessageDetails getLastMessage() {
        return lastMessage;
    }

    class UserNoPassword {
       private String username;
       private String displayName;
       private String profilePic;


       public UserNoPassword(String username, String displayName, String profilePic) {
           this.username = username;
           this.displayName = displayName;
           this.profilePic = profilePic;
       }

       public String getUsername() {
           return username;
       }

       public void setUsername(String username) {
           this.username = username;
       }

       public String getDisplayName() {
           return displayName;
       }

       public void setDisplayName(String displayName) {
           this.displayName = displayName;
       }

       public String getProfilePic() {
           return profilePic;
       }

       public void setProfilePic(String profilePic) {
           this.profilePic = profilePic;
       }
   }

   class MessageDetails{
       private int id;
       private String created;
       private String content;

       public MessageDetails(int id, String created, String content) {
           this.id = id;
           this.created = created;
           this.content = content;
       }

       public int getId() {
           return id;
       }

       public void setId(int id) {
           this.id = id;
       }

       public String getCreated() {
           return created;
       }

       public void setCreated(String created) {
           this.created = created;
       }

       public String getContent() {
           return content;
       }

       public void setContent(String content) {
           this.content = content;
       }
   }
}
