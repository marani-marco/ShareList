package com.marco.sharelist.entity;

import java.util.List;
import java.util.Set;

public class ShareList {

    private String title;
    private String description;
    private String userId;
    private String type;
    private List<ShareItem> items;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ShareItem> getItems() {
        return items;
    }

    public void setItems(List<ShareItem> items) {
        this.items = items;
    }
}
