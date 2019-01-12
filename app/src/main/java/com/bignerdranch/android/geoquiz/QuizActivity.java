package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    // Notice the m prefix on the two member variable names
    // This is an Android naming convention.
    // View objects
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTexView;
    // Model objects
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;
    @Override
    // The onCreate(Bundle) method is called when an instance of the activity
    //  subclass is created.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(int layoutResID) inflates a layout and puts it on the screen
        setContentView(R.layout.activity_quiz);
        // You can get a reference to an inflated widget by calling
        //  public View findViewById(int id)
        // This method accepts a resource ID of a widget and returns a (generic) View object
        // Because it returns a View object, must downcast to appropriate widget
        mQuestionTexView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTexView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextQuestion();
            }
        });
        mTrueButton = (Button) findViewById(R.id.true_button);
        // The setOnClickListener(OnClickListener) method takes a listener as its argument.
        // In particular, it takes an object that implements OnClickListener.
        // This listener is implemented as an anonymous inner class.
        // Here you create a new, nameless class and pass its entire implementation.
        // The OnClickListener interface's sole method is onClick(View), and must be implemented.
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextQuestion();
            }
        });
        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPrevQuestion();
            }
        });
        updateQuestion();
    }
    // Private methods
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTexView.setText(question);
    }
    private void goToNextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
    }
    private void goToPrevQuestion() {
        mCurrentIndex = (mCurrentIndex == 0) ? mQuestionBank.length - 1 : mCurrentIndex - 1;
        updateQuestion();
    }
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResID = 0;
        if (userPressedTrue == answerIsTrue)
            messageResID = R.string.correct_toast;
        else
            messageResID = R.string.incorrect_toast;
        // A toast is a short message that informs the user of something, but does
        //  not require any input or action.
        // Note that for the context argument you can't simply use 'this', since
        //  you are inside of the anonymous inner class, not QuizActivity.
        // After creating the Toast, call show to get it on the screen.
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show();
    }
}
