package com.suchi.flipcardanimationlist;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

public class FlipAnimator {
    private static String TAG = FlipAnimator.class.getSimpleName();
    private static AnimatorSet buttonIn, topOut, buttonOut, topIn;

    /**
     * Performs flip animation on two views
     */
    public static void flipView(final Context context, final View back, final View front, final boolean showFront, final int position) {
        buttonIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.card_flip_button_in);
        topOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.card_flip_top_out);
        buttonOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.card_flip_button_out);
        topIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.card_flip_top_in);

        final AnimatorSet showFrontAnim = new AnimatorSet();
        final AnimatorSet showBackAnim = new AnimatorSet();

        buttonIn.setTarget(back);
        topOut.setTarget(front);
        showFrontAnim.playTogether(buttonIn, topOut);
        showFrontAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                setEventData(position, showFront);
                //Toast.makeText(context, "Animation end", Toast.LENGTH_SHORT).show();
            }
        });
        buttonOut.setTarget(back);
        topIn.setTarget(front);
        showBackAnim.playTogether(topIn, buttonOut);
        showBackAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setEventData(position, showFront);
            }
        });
        if (showFront) {
            showFrontAnim.start();
        } else {
            showBackAnim.start();
        }
    }

    private static void setEventData(int position, boolean showFront) {
        EventData data = new EventData();
        int count = position + 1;
        MainActivity.isFront = showFront;
        data.setFront(showFront);
        data.setPosition(count);
        EventBus.getDefault().post(data);
    }
}
