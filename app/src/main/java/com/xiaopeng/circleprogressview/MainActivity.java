package com.xiaopeng.circleprogressview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CircleProgressView circleprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.circleprogress = (CircleProgressView) findViewById(R.id.circle_progress);
        circleprogress.setDrawable(this.getResources().getDrawable(R.drawable.apple));
        circleprogress.setRotate(true);
        circleprogress.start(6);

    }
}
