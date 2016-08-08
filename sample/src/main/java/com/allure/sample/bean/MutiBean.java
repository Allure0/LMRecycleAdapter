package com.allure.sample.bean;

import java.io.Serializable;

/**
 * Created by luomin on 16/8/5.
 */
public class MutiBean implements Serializable {

    public static final int ITEM_TYPE_CONTENT= 0;
    public static final int ITEM_TYPE_PIC = 1;


    private String content;


    private int resoucre;

    private int itemType;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getResoucre() {
        return resoucre;
    }

    public void setResoucre(int resoucre) {
        this.resoucre = resoucre;
    }
}
