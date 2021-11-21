package com.azizmelek.android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class homePage extends AppCompatActivity {
    private static final String[] sectors = {"You've got +100% of your current score", "you've got an additional Spin", "You've got +50% of your current score", "You've got +75% of your current score", "you've got an additional Spin", "Your next wrong answer won't be counted", "You've got +25% of your current score", "Oops!,you've got nothing"};
    private static final int[] sectorDegrees = new int[sectors.length];
    private static final Random random = new Random();
    private int degree = 0;
    private ImageView wheel;
    ImageView spinBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        spinBtn = findViewById(R.id.spin);
        wheel = findViewById(R.id.wheel);
        setupSectorDegrees();

        spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spin();
            }
        });
    }
    private void spin(){
        degree = random.nextInt(sectors.length-1);
        RotateAnimation rotate = new RotateAnimation(0 , (360 * sectors.length) + sectorDegrees[degree] , RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF , 0.5f);
        rotate.setDuration(3600);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new DecelerateInterpolator());
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                spinBtn.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(homePage.this, sectors[sectors.length - degree - 1], Toast.LENGTH_SHORT).show();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(sectors.length - degree  == 2 ||sectors.length - degree  == 5){
                    spinBtn.setEnabled(true);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("sector_index" , sectors.length - degree - 1);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        wheel.startAnimation(rotate);
    }
    private void setupSectorDegrees(){
        int sectorDegree = 360 / sectors.length;
        for(int i = 0; i< sectors.length ;i++){
            sectorDegrees[i] = (i+1) * sectorDegree;
        }
    }


}
