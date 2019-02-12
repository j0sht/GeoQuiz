package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    // Private static member variables
    // TAG is used in the first parameter to android.Util.Log.d(String tag, String msg)
    // TAG makes it easy to determine the source of the msg
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    // Notice the m prefix on the two member variable names
    // This is an Android naming convention.
    // View objects
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
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
    private boolean mIsCheater;
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
        if (savedInstanceState != null)
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
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
                updateQuestion();
            }
        });
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Cheat activity

                // An intent is an object that a component can use to communicate with the OS.
                // The only components you have seen so far are activities, but there are also
                //  services, broadcast receivers, and content providers.
                // There are many different constructors for Intent's
                // In this one, the first argument tells the ActivityManager which application
                //  package the activity class can be found in.
                // The second argument specifies the activity class that the ActivityManager
                //  should start.
                // When you create an Intent with a Context and a Class object, you are
                //  creating an explicit intent. You use explicit intents to start activities
                //  within your application. When an activity in your application wants
                //  to start an activity in another application you create an implicit intent.
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                // startActivity is a call to the Android OS, in particular, a part of the OS
                //  called the ActivityManager. The ActivityManager then creates the Activity
                //  instance and calls its onCreate(Bundle) method
                // The second argument is a 'request code'. It is a user-defined integer that
                //  is sent to the child activity and then received back by the parent. It is
                //  used when an activity starts more than one type of child activity and needs
                //  to know who is reporting back.
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
                // The ActivityManager check's the package's manifest for a declaration with
                //  the same name as the specified Class. If it does not find it, you get
                //  a ActivityNotFoundException
            }
        });
        updateQuestion();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != AppCompatActivity.RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null)
                return;
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    // Private methods
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTexView.setText(question);
    }
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResID = 0;
        if (mIsCheater)
            messageResID = R.string.judgment_toast;
        else {
            if (userPressedTrue == answerIsTrue)
                messageResID = R.string.correct_toast;
            else
                messageResID = R.string.incorrect_toast;
        }
        // A toast is a short message that informs the user of something, but does
        //  not require any input or action.
        // Note that for the context argument you can't simply use 'this', since
        //  you are inside of the anonymous inner class, not QuizActivity.
        // After creating the Toast, call show to get it on the screen.
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show();
    }
}
