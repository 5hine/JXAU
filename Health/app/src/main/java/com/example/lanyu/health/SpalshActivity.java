package com.example.lanyu.health;

import android.os.Bundle;
import android.os.Handler;
import com.example.lanyu.health.base.BaseActivity;

/**
 * Created by lanyu on 2018/12/24.
 */
public class SpalshActivity extends BaseActivity {

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_spalsh);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startIntent(LoginActivity.class);
                finish();
            }
        },2000);
    }

}
