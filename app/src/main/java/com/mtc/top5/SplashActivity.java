package com.mtc.top5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.concurrent.CountDownLatch;

public class SplashActivity extends AppCompatActivity {

    private TextView appName;
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final CountDownLatch latch = new CountDownLatch(1);
        mContext = this;

        DBqueries thread = new DBqueries(latch, mContext);

        new Thread(thread).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_splash);

        appName = findViewById(R.id.app_name);

        Typeface typeface = ResourcesCompat.getFont( this, R.font.blacklist);
        appName.setTypeface(typeface);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        appName.setAnimation(anim);


        new Thread() {

            @Override
            public void run()
            {

                try {
                    sleep( 3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }


        }.start();

    }
}