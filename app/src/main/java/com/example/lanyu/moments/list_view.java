package com.example.lanyu.moments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import bean.User;


public class list_view extends Activity {
    static User[] user = new User[100];
    static String[] str = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        List<User> list = new ArrayList<>();
        //头像  用户名  内容 评论按钮图
        list.add(new User(R.drawable.timg, "管理员", "第一条", R.drawable.pinglun));
        //Intent intent = getIntent();
        //String content1 = intent.getExtras().getString("content");
        SharedPreferences spf = getSharedPreferences("username", Context.MODE_PRIVATE);
        //SharedPreferences.Editor edit = spf.edit();
        String name = spf.getString("username", "default");
        int num = spf.getInt("number", 0);

        if (num != 0) {
            for (int i = 0; i < num; i++) {
                str[i] = spf.getString("content" + (i + 1), "default");
                ;
                user[i] = new User(R.drawable.timg, name, str[i], R.drawable.pinglun);
                list.add(0, user[i]);
            }
        }


        ListViewAdapt adapter = new ListViewAdapt(this, R.layout.list_view_first_layout, list);
        ListView listView = ((ListView) findViewById(R.id.show_list));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) view.findViewById(R.id.username)).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        Button publish_button = (Button) findViewById(R.id.button6);
        publish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(list_view.this, publish.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view, menu);
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

