package com.example.myapplication.ui.main;

public class MatchExampleItem {
    private int mImageResource;
    private String mText1;
    private String mText2;

    public MatchExampleItem(int ImageRes, String text1, String text2)
    {
        this.mImageResource = ImageRes;
        this.mText1 = text1;
        this.mText2 = text2;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }
}
