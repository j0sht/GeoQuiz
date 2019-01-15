package com.bignerdranch.android.geoquiz;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {
    // Private static member variables
    // TAG is used in the first parameter to android.Util.Log.d(String tag, String msg)
    // TAG makes it easy to determine the source of the msg
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CORRECT = "correctCount";
    private static final String KEY_ANSWER = "answerCount";
    private static final String KEY_SCORE = "scoreDisplayed";
    private static final String KEY_ANSWERED = "answeredArray";
    // Notice the m prefix on the two member variable names
    // This is an Android naming convention.
    // View objects
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
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
    private boolean scoreDisplayed = false;
    private int correctCount = 0;
    private int answerCount = 0;
    // ACTIVITY LIFECYCLE METHODS
    // The onCreate(Bundle) method is called when an instance of the activity
    //  subclass is created.
    // The OS calls this method after the activity instance is created, but before
    //  it is put on screen.
    // Typically, an activity overrides onCreate(Bundle) to prepare the specifics of
    //  its UI:
    //   1) Inflating widgets and putting them on screen (in setContentView(int))
    //   2) Getting references to inflated widgets
    //   3) Setting listeners on widgets to handle user interaction
    //   4) Connecting to an external model data
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        // setContentView(int layoutResID) inflates a layout and puts it on the screen
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            scoreDisplayed = savedInstanceState.getBoolean(KEY_SCORE, false);
            correctCount = savedInstanceState.getInt(KEY_CORRECT, 0);
            answerCount = savedInstanceState.getInt(KEY_ANSWER, 0);
            boolean[] answered = savedInstanceState.getBooleanArray(KEY_ANSWERED);
            for (int i = 0; i < mQuestionBank.length; i++)
                mQuestionBank[i].setAnswerGiven(answered[i]);
        }
        // You can get a reference to an inflated widget by calling
        //  public View findViewById(int id)
        // This method accepts a resource ID of a widget and returns a (generic) View object
        // Because it returns a View object, must downcast to appropriate widget
        mQuestionTexView = (TextView) findViewById(R.id.question_text_view);
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
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateView();
            }
        });
        updateView();
    }
    @Override
    protected void onStart() {
        // onStart is called by the OS when the Activity goes from stopped state
        //  to the paused state
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    protected void onResume() {
        // onResume is called by the OS when the Activity goes from paused state to
        //  resumed state
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    protected void onPause() {
        // onPause is called by the OS when the Activity goes from the resumed state to
        //  the paused state.
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    protected void onStop() {
        // onStop is called by the OS when the Activity goes from paused to stopped state
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    protected void onDestroy() {
        // onDestroy is called by the OS when the Activity goes from stopped to nonexistent
        //  state. Activity is no longer in memory after onDestroy.
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
    // Other Activity Methods
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // onSaveInstanceState(Bundle) is called before onStop(), except when the
        //  user presses the back button.
        // Default implementation directs all of the activity's views to save their state
        //  as data in the Bundle object.
        // A Bundle is a structure that maps string keys to values of certain limited types.
        //  (primitive types and classes that implement Serializable or Parceable interfaces)
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBoolean(KEY_SCORE, scoreDisplayed);
        outState.putInt(KEY_CORRECT, correctCount);
        outState.putInt(KEY_ANSWER, answerCount);
        boolean[] answered = new boolean[mQuestionBank.length];
        for (int i = 0; i < mQuestionBank.length; i++)
            answered[i] = mQuestionBank[i].isAnswerGiven();
        outState.putBooleanArray(KEY_ANSWERED, answered);
    }
    // Private methods
    private void checkAnswer(boolean userPressedTrue) {
        answerCount++;
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResID = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResID = R.string.correct_toast;
            correctCount++;
        } else {
            messageResID = R.string.incorrect_toast;
        }
        // A toast is a short message that informs the user of something, but does
        //  not require any input or action.
        // Note that for the context argument you can't simply use 'this', since
        //  you are inside of the anonymous inner class, not QuizActivity.
        // After creating the Toast, call show to get it on the screen.
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show();
        mQuestionBank[mCurrentIndex].setAnswerGiven(true);
        updateView();
    }
    private void updateView() {
        boolean answerGiven = mQuestionBank[mCurrentIndex].isAnswerGiven();
        mTrueButton.setEnabled(!answerGiven);
        mFalseButton.setEnabled(!answerGiven);
        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTexView.setText(question);
        if ((answerCount == mQuestionBank.length) && !scoreDisplayed) {
            Log.d(TAG, "Displayed score");
            double score = (double) correctCount / (double) mQuestionBank.length;
            String toastText = String.format(
                    "You got %d/%d = %.2f%%",
                    correctCount, mQuestionBank.length, score*100
            );
            Toast.makeText(this, toastText, Toast.LENGTH_LONG).show();
            scoreDisplayed = true;
        }
    }
}
