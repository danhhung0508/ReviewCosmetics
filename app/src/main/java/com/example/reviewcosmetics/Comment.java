package com.example.reviewcosmetics;

public class Comment {
    private String nameUser;
    private String imageUser;
    private String cmt;

    public Comment() {

    }


    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public Comment(String nameUser, String imageUser, String cmt) {
        this.nameUser = nameUser;
        this.imageUser = imageUser;
        this.cmt = cmt;
    }
}
