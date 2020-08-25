package com.example.hope;

public class detailpost {

    public static final int TEXT_TYPE=0;
    public static final int IMAGE_TYPE=1;
    //public  static final int likebutton=3;

    public int type;
    public String title;
    public String description;
    public int userdp;
    public int imgUpload;            //works as imgview



    public detailpost(){

    }


    public detailpost(int type, String title,String description,int userdp,int imgUpload)
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

    public int getImgUpload() {
        return imgUpload;
    }

    public void setImgUpload(int imgUpload) {
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

    public int getUserdp() {
        return userdp;
    }

    public void setUserdp(int userdp) {
        this.userdp = userdp;
    }
}

