package com.example.lanyu.moments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import util.HttpHelp;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册按钮实现注册页面跳转
        Button register_button = (Button) findViewById(R.id.button2);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 给bnt1添加点击响应事件
                Intent intent = new Intent(MainActivity.this, register.class);
                //启动
                startActivity(intent);

            }
        });
        //登录按钮跳转
        Button login_button = (Button) findViewById(R.id.button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(runnable).start();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ((TextView) findViewById(R.id.show_http)).setText(msg.obj.toString());

        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {

                String name = ((EditText) findViewById(R.id.editText)).getText().toString();
                //使用SharedPreferences 实现用户名存储
                SharedPreferences spf = getSharedPreferences("username", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = spf.edit();
                edit.putString("username", name);
                edit.putInt("number", 0);
                edit.putString("content", "");
                edit.apply();
                String pass = ((EditText) findViewById(R.id.editText2)).getText().toString();
                URL url = new URL("http://172.20.10.3:8080/Android_server/http?name=android_test");
                HttpURLConnection urlConnetction = (HttpURLConnection) url.openConnection();
                urlConnetction.setConnectTimeout(5 * 1000);
                urlConnetction.setRequestMethod("POST");
                String body = "type=" + URLEncoder.encode("login", "utf-8") + "&userName=" + URLEncoder.encode(name, "utf-8") + "&passWord=" + URLEncoder.encode(pass, "utf-8");
                urlConnetction.getOutputStream().write(body.getBytes());
                InputStream inputStream = urlConnetction.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                        "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuffer.append(temp);
                    //登录成功判断  并使用Bundle将用户名 传递
                    if (stringBuffer.toString().equals("Login Success")) {
                        Intent intent = new Intent(MainActivity.this, list_view.class);

                        startActivity(intent);
                        finish();
                    } else {//返回失败
                        Message message = new Message();
                        message.obj = stringBuffer.toString();
                        handler.sendMessage(message);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
