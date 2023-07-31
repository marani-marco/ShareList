package com.marco.sharelist.entity;

public class ShareItem {

    private String listId;
    private String title;
    private String description;
    private String userAssigned;
    private Boolean completed;
    private Boolean confirmComplete;

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
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

    public String getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(String userAssigned) {
        this.userAssigned = userAssigned;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getConfirmComplete() {
        return confirmComplete;
    }

    public void setConfirmComplete(Boolean confirmComplete) {
        this.confirmComplete = confirmComplete;
    }
}
