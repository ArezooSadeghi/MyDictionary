package com.example.mydictionary.model;

public class Word {
    private String mName;
    private String mMean;
    private int mId;
    private int mFav;

    public Word() {
    }

    public Word(String name, String mean, int id) {
        mName = name;
        mMean = mean;
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setMean(String mean) {
        mMean = mean;
    }

    public void setFav(int fav) {
        mFav = fav;
    }

    public String getName() {
        return mName;
    }

    public String getMean() {
        return mMean;
    }

    public int getId() {
        return mId;
    }

    public int getFav() {
        return mFav;
    }
}
