package com.allure.sample.bean;

import java.io.Serializable;

/**
 * Created by luomin on 16/8/5.
 */
public class TestBean implements Serializable{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
