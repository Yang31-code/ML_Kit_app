package com.google.mlkit.vision.demo.preference.entity;

public class SettingEntity {
    private Integer img;
    private String title;

    @Override
    public String toString() {
        return "SettingEntity{" +
                "img=" + img +
                ", title='" + title + '\'' +
                '}';
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SettingEntity() {
    }

    public SettingEntity(Integer img, String title) {
        this.img = img;
        this.title = title;
    }
}
