package com.tectoy.fullscanner.model;

import java.io.Serializable;

/**
 * @company TECTOY
 * @department development and support
 *
 * @author fenascimento
 *
 */

public class Product implements Serializable{
    private long id;
    private String name;
    private String desc;
    private double value;
    private String code;
    private int image;

    public Product(long id, String name, String desc, double value, String code, int image) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.value = value;
        this.code = code;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
