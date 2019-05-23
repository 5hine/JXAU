package com.example.lanyu.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.lanyu.health.base.BaseActivity;

/**
 * Created by lanyu on 2018/12/26.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button mornrunBtn,exerciseBtn,walkBtn,rideBtn,swimBtn,ballBtn,evenrunBtn,addBtn;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mornrunBtn.setOnClickListener(this);
        exerciseBtn.setOnClickListener(this);
        walkBtn.setOnClickListener(this);
        rideBtn.setOnClickListener(this);
        swimBtn.setOnClickListener(this);
        ballBtn.setOnClickListener(this);
        evenrunBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
    }

    private void initView(){
        mornrunBtn = (Button)findViewById(R.id.mornrun_search_btn);
        exerciseBtn =  (Button)findViewById(R.id.exercise_search_btn);
        walkBtn =  (Button)findViewById(R.id.walk_search_btn);
        rideBtn =  (Button)findViewById(R.id.ride_search_btn);
        swimBtn = (Button)findViewById(R.id.swim_search_btn);
        ballBtn = (Button)findViewById(R.id.ball_search_btn);
        evenrunBtn = (Button)findViewById(R.id.evenrun_search_btn);
        addBtn = (Button)findViewById(R.id.search_add_btn);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,SearchActivity.class);
        switch (v.getId()){
            case R.id.search_add_btn:
                startIntent(AddActivity.class);
                //finish();
                break;
            case R.id.mornrun_search_btn:
                intent.putExtra("name","晨跑");
                break;
            case R.id.exercise_search_btn:
                intent.putExtra("name","晨练");
                break;
            case R.id.walk_search_btn:
                intent.putExtra("name","行走");
                break;

            case R.id.ride_search_btn:
                intent.putExtra("name","骑行");
                break;
            case R.id.swim_search_btn:
                intent.putExtra("name","游泳");
                break;
            case R.id.ball_search_btn:
                intent.putExtra("name","球类");
                break;
            case R.id.evenrun_search_btn:
                intent.putExtra("name", "夜间跑步");
                break;

        }
        if(v.getId() != R.id.search_add_btn){

            startActivity(intent);

        }
    }

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