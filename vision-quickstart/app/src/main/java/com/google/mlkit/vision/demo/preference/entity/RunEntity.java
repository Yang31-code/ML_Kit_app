package com.google.mlkit.vision.demo.preference.entity;

public class RunEntity {
    public String name;
    private String title;
    private String img;
    private Integer imgInt;


    @Override
    public String toString() {
        return "RunEntity{" +
                "title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", imgInt=" + imgInt +
                '}';
    }

    public Integer getImgInt() {
        return imgInt;
    }

    public void setImgInt(Integer imgInt) {
        this.imgInt = imgInt;
    }

    public RunEntity(String title, String img, Integer imgInt, String name) {
        this.title = title;
        this.img = img;
        this.imgInt = imgInt;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public RunEntity() {
    }

    public RunEntity(String title, String img) {
        this.title = title;
        this.img = img;
    }
}
