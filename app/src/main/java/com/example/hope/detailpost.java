package com.example.hope;

public class detailpost {

    public static final int TEXT_TYPE=0;
    public static final int IMAGE_TYPE=1;

    public int type;
    public String title;
    public String description;
    public String userdp;
    public String imgUpload;
    private String postKey;


    public detailpost(){

    }


    public detailpost(int type, String title,String description,String userdp,String imgUpload)
    {
        this.type=type;
        this.imgUpload=imgUpload;
        this.title=title;
        this.description = description;
        this.userdp = userdp;

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImgUpload() {
        return imgUpload;
    }

    public void setImgUpload(String imgUpload) {
        this.imgUpload = imgUpload;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserdp() {
        return userdp;
    }

    public void setUserdp(String userdp) {
        this.userdp = userdp;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getPostKey() {
        return postKey;
    }
}

