package com.rajit.android.models;

/**
 * Created by rajit on 4/12/17.
 */

public class User {
    private String id;
    private String name;
    private String password;
    private String nickname;
    private String designation;
    private String id_no;
    private String phone;
    private String created_at;
    private String updated_at;

    public User(String id, String name, String password, String nickname, String designation, String id_no, String phone, String created_at, String updated_at) {

        this.id = id;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.designation = designation;
        this.id_no = id_no;
        this.phone = phone;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
