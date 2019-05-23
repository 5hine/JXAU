package com.example.lanyu.health.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by lanyu on 2018/12/24.
 */
public class BaseActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    protected void toast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    protected void startIntent(Class classIntent){
       /* Intent intent = new Intent();
        intent.setClass(this,classIntent);
        startActivity(intent);
        */
        startActivity(new Intent().setClass(this,classIntent));
    }

}
