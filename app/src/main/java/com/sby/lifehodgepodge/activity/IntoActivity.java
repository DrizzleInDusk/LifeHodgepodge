package com.sby.lifehodgepodge.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.sby.lifehodgepodge.R;

public class IntoActivity extends Activity {

    private ImageView into_anim_iv;
    private ValueAnimator animator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into);
        into_anim_iv= (ImageView) findViewById(R.id.into_anim_iv);
    }

    //一旦act加载完毕，就执行动画
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            setAnimator();
        }
    }

    private void setAnimator(){
        animator=ObjectAnimator.ofFloat(into_anim_iv,"alpha",0f,1f);
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(IntoActivity.this,MainActivity.class));
                //关闭此页防止用户按back
                finish();
            }
        });
        animator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animator.pause();
    }
}
