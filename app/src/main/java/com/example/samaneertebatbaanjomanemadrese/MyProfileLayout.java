package com.example.samaneertebatbaanjomanemadrese;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MyProfileLayout extends RelativeLayout implements View.OnClickListener {
    private static final long REVEAL_DURATION = 1000;//mili saniye
    private View rootview;
    private AppCompatImageView avatar, menu, cover;
    private RelativeLayout layoutReveal;
    private LinearLayout layoutBtn;
    private AppCompatButton send, search, chatlist;



    public MyProfileLayout(Context context) {
        super(context);
        init(context);
    }

    public MyProfileLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AppCompatImageView getAvatar() {
        return avatar;
    }

    public AppCompatImageView getCover() {
        return cover;
    }

    public AppCompatButton getSend() {
        return send;
    }

    public AppCompatButton getSearch() {
        return search;
    }

    public AppCompatButton getChatlist() {
        return chatlist;
    }

    private void init(Context context) {
        rootview = inflate(context, R.layout.layout_profile, this);
        avatar = (AppCompatImageView) rootview.findViewById(R.id.profile_imgv_avatar);
        menu = (AppCompatImageView) rootview.findViewById(R.id.profile_imgv_menu);
        cover = (AppCompatImageView) rootview.findViewById(R.id.profile_imgv_cover);
        send = (AppCompatButton) rootview.findViewById(R.id.profile_btn_send);
        search = (AppCompatButton) rootview.findViewById(R.id.profile_btn_search);
        chatlist = (AppCompatButton) rootview.findViewById(R.id.profile_btn_chatlist);
        layoutReveal = (RelativeLayout) rootview.findViewById(R.id.profile_rl_reveal);
        layoutBtn = (LinearLayout) rootview.findViewById(R.id.profile_lil_btns);

        menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int centerX = ( menu.getRight() + menu.getLeft() )/2;
        int centerY =( menu.getBottom() + menu.getTop() )/2;
        float radius=(float) Math.hypot(centerX - cover.getLeft(), cover.getHeight());
        if (layoutReveal.getVisibility() == VISIBLE) {
            hide(centerX, centerY, radius);
        } else {
            show(centerX, centerY, radius);
        }


    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private void show(int centerX, int centerY, float radius) {
        Animator animator = ViewAnimationUtils.createCircularReveal(layoutReveal, centerX, centerY, 0, radius);
        animator.setDuration(REVEAL_DURATION);
        layoutReveal.setVisibility(VISIBLE);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                layoutReveal.setVisibility(VISIBLE);
                menu.setImageResource(R.drawable.close);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                layoutBtn.setVisibility(VISIBLE);
                Animation fadein = AnimationUtils.loadAnimation(rootview.getContext(), R.anim.fadein);
                layoutBtn.startAnimation(fadein);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private void hide(int centerX, int centerY, float radius) {
        Animator animator = ViewAnimationUtils.createCircularReveal(layoutReveal, centerX, centerY, radius, 0);
        animator.setDuration(REVEAL_DURATION);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                menu.setImageResource(R.drawable.list);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                layoutReveal.setVisibility(GONE);
                layoutBtn.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

    }
}
