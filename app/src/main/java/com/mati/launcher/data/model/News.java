package com.mati.launcher.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {

    @SerializedName("values")
    @Expose
    private List<Value> values;

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public static class Value {
        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("Name")
        @Expose
        private String Name;

        @SerializedName("imgurl")
        @Expose
        private String imgurl;

        @SerializedName("time")
        @Expose
        private String time;

        @SerializedName("data")
        @Expose
        private String data;

        @SerializedName("likes")
        @Expose
        private int likes;

        @SerializedName("url")
        @Expose
        private String url;

        @SerializedName("show")
        @Expose
        private int show;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getShow() {
            return show;
        }

    }
}