package com.example.lanyu.moments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

import bean.User;


public class publish extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        //取消按钮
        Button cancel_button = (Button) findViewById(R.id.button4);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //发布按钮
        Button publish_button = (Button) findViewById(R.id.button5);
        publish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用Bundle对象传递  User对象  借用了Serializable接口
                String content = ((TextView) findViewById(R.id.editText6)).getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("content", content);
                // 传值记录 发布动态数量  内容
                SharedPreferences spf = getSharedPreferences("username", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = spf.edit();
                int num = spf.getInt("number", 0);
                num = num + 1;
                edit.putString("content" + num, content);
                edit.putInt("number", num);
                edit.apply();
                Intent intent = new Intent(publish.this, list_view.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_publish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
