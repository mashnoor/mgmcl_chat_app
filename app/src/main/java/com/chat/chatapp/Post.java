package com.chat.chatapp;

/**
 * Created by Menon on 7/25/16.
 */
public class Post {

    String userPhone;
    String voiceFile;
    String time;
    String userName;


    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getVoiceFile() {
        return voiceFile;
    }

    public String getTime() {
        return time;
    }

    public Post(String name, String userPhone, String voiceFile, String time) {

        this.userName = name;
        this.userPhone = userPhone;
        this.voiceFile = voiceFile;
        this.time = time;
    }
}
