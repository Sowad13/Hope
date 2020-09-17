package com.example.hope;

public class registerUser {

    String Id;


    String registername;
    String registerimage;
    static String postKey;

    public registerUser() {
    }

    public registerUser(String registername, String registerimage) {
        this.registername = registername;
        this.registerimage = registerimage;
        this.Id = Id;
    }

    public static void setPostKey(String postKey) {
        registerUser.postKey = postKey;
    }

    public static String getPostKey() {
        return postKey;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRegistername() {
        return registername;
    }

    public void setRegistername(String registername) {
        this.registername = registername;
    }

    public String getRegisterimage() {
        return registerimage;
    }

    public void setRegisterimage(String registerimage) {
        this.registerimage = registerimage;
    }
}
