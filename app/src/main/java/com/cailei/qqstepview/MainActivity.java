package com.cailei.qqstepview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

public class MainActivity extends AppCompatActivity {

    ObjectAnimator objectAnimator;
    ValueAnimator valueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final QQStepView qqStepView=findViewById(R.id.stepView);
        qqStepView.setMaxStep(4000);
        valueAnimator=ObjectAnimator.ofInt(0,2000);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value= (int) animation.getAnimatedValue();
                qqStepView.setCurrentStep(value);

            }
        });
        valueAnimator.start();

    }
}
