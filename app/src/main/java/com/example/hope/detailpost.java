package com.example.hope;

public class detailpost {

    public static final int TEXT_TYPE=0;
    public static final int IMAGE_TYPE=1;

    public int type;
    public int data;    //works as imgview
    public String title;
    public String description;
    public int userdp;
   // public String picadd;



    public detailpost(){

    }


    public detailpost(int type, String title,String description,int userdp,int data)
    {
        this.type=type;
        this.data=data;
        this.title=title;
        this.description = description;
        this.userdp = userdp;
       // this.picadd = picadd;

    }


}

