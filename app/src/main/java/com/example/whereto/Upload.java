package com.example.whereto;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mImageUrl;
    private String mKey;

    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String key) {
        this.mKey = key;
    }

    public Upload(){

    }
    public Upload(String imageUrl){
        mImageUrl = imageUrl;
    }

    public String getmImageUrl(){
        return mImageUrl;
    }
    public void setmImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }
}
