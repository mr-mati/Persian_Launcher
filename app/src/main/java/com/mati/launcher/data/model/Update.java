package com.mati.launcher.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Update {
    @SerializedName("values")
    @Expose
    private List<Value> values;

    public Update(List<Value> values) {
        this.values = values;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public static class Value {
        @SerializedName("Link")
        @Expose
        private String Link;
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("mandatory")
        @Expose
        private Integer mandatory;
        @SerializedName("version_code")
        @Expose
        private int version_code;
        @SerializedName("version_name")
        @Expose
        private String version_name;

        public Value(String Link, int id, int mandatory, int version_code, String version_name) {
            this.Link = Link;
            this.id = id;
            this.mandatory = mandatory;
            this.version_code = version_code;
            this.version_name = version_name;
        }

        public String getLink() {
            return Link;
        }

        public void setLink(String Link) {
            this.Link = Link;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Integer getMandatory() {
            return mandatory;
        }

        public void setMandatory(Integer mandatory) {
            this.mandatory = mandatory;
        }

        public int getVersion_code() {
            return version_code;
        }

        public void setVersion_code(int version_code) {
            this.version_code = version_code;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }
    }
}

