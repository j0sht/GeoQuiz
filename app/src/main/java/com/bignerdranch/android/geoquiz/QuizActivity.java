package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    // Notice the m prefix on the two member variable names
    // This is an Android naming convention.
    private Button mTrueButton;
    private Button mFalseButton;
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
        mTrueButton = (Button) findViewById(R.id.true_button);
        // The setOnClickListener(OnClickListener) method takes a listener as its argument.
        // In particular, it takes an object that implements OnClickListener.
        // This listener is implemented as an anonymous inner class.
        // Here you create a new, nameless class and pass its entire implementation.
        // The OnClickListener interface's sole method is onClick(View), and must be implemented.
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // A toast is a short message that informs the user of something, but does
                //  not require any input or action.
                // Note that for the context argument you can't simply use 'this', since
                //  you are inside of the anonymous inner class, not QuizActivity.
                // After creating the Toast, call show to get it on the screen.
                Toast.makeText(QuizActivity.this,
                        R.string.correct_toast,
                        Toast.LENGTH_SHORT).show();
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizActivity.this,
                        R.string.incorrect_toast,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
