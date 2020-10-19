package com.example.mydictionary.model;

import java.util.UUID;

public class Word {
    private String mName;
    private String mMean;
    private UUID mId;

    public Word() {
        mId = UUID.randomUUID();
    }

    public Word(String name, String mean) {
        mId = UUID.randomUUID();
        mName = name;
        mMean = mean;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setMean(String mean) {
        mMean = mean;
    }

    public String getName() {
        return mName;
    }

    public String getMean() {
        return mMean;
    }

    public UUID getId() {
        return mId;
    }
}
