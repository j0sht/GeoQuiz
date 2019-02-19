package com.bignerdranch.android.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private TextView mAPITextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mAPITextView = (TextView) findViewById(R.id.api_level_text_view);
        String apiLevel = String.format("API Level %d", Build.VERSION.SDK_INT);
        mAPITextView.setText(apiLevel);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue)
                    mAnswerTextView.setText(R.string.true_button);
                else
                    mAnswerTextView.setText(R.string.false_button);
                setAnswerShownResult(true);

                // ViewAnimationUtils is only available in API 21 or greater
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();

                    // createCircularReveal creates an animator from a few parameters:
                    //  1. The View that will be hidden or shown
                    //  2 & 3. Set a center position (cx, cy)
                    //  4. Start radius
                    //  5. End radius
                    // This particular animation hides the mShowAnswerButton, it's start
                    //  radius is the width of the button, and its end radius is 0, so its
                    //  radius moves from the width of the button to 0.
                    Animator anim = ViewAnimationUtils.createCircularReveal(
                            mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        // Set result allows a child activity to send data back to the calling parent activity.
        // The first argument is a result code. Typically one of two predefined constants,
        //  Activity.RESULT_OK or Activity.RESULT_CANCELLED
        // Calling setResult is not required by the child. If you do not need to distinguish
        //  between results or receive arbitrary data on an intent, then you can let the OS
        //  send a default result code.
        // A result code is always returned to the parent if the child activity was started
        //  with startActivityForResult()
        // If setResult is not called, then when the user presses the Back button, the parent
        //  will receive Activity.RESULT_CANCELLED
        setResult(RESULT_OK, data);
    }
}
