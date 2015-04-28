package com.tony.miniblog.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import com.tony.miniblog.R;

public class SplashActivity extends Activity implements Animation.AnimationListener {
    /**
     * Called when the activity is first created.
     */

    private View v_splash;
    private TextView tv_version_info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.layout_splash);

        v_splash = (View) this.findViewById(R.id.layout_splash);
        //tv_version_info = (TextView)this.findViewById(R.id.tv_splash_version_info);

        //create an animation
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000);
        animation.setAnimationListener(this);

        //Animation animation1 = new ScaleAnimation()

        v_splash.startAnimation(animation);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //goto main activity
        Intent intentGomain = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intentGomain);
        this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
