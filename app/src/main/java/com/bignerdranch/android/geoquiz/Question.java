package com.bignerdranch.android.geoquiz;

public class Question {
    // Member variables
    private int mTextResID;
    private boolean mAnswerTrue;
    private boolean mAnswerGiven;
    // Constructor
    public Question(int textResID, boolean answerTrue) {
        mTextResID = textResID;
        mAnswerTrue = answerTrue;
        mAnswerGiven = false;
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
    public boolean isAnswerGiven() {
        return mAnswerGiven;
    }
    public void setAnswerGiven(boolean answerGiven) {
        mAnswerGiven = answerGiven;
    }
}
