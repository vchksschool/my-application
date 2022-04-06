package com.mtc.top5;

public class ProfileModel
{
    public ProfileModel(String email, String name,int UID) {

        this.email = email;
        this.name =name;
        this.uid = UID;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String name;
    private String email;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {

        System.out.println("uid changed");
        this.uid = uid;
    }

    private int uid;

}
