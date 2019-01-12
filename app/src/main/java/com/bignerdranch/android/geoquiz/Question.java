package com.bignerdranch.android.geoquiz;

public class Question {
    // Member variables
    private int mTextResID;
    private boolean mAnswerTrue;
    // Constructor
    public Question(int textResID, boolean answerTrue) {
        mTextResID = textResID;
        mAnswerTrue = answerTrue;
    }
    // Getters and setters
    public int getTextResID() {
        return mTextResID;
    }
    public void setTextResID(int textResID) {
        mTextResID = textResID;
    }
    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }
    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
