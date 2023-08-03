package com.marco.sharelist.dto;

import com.marco.sharelist.entity.ShareList;

public class Response {

    private String result;
    private String errorDescription;
    private ShareList shareList = new ShareList();

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public ShareList getShareList() {
        return shareList;
    }

    public void setShareList(ShareList shareList) {
        this.shareList = shareList;
    }
}
