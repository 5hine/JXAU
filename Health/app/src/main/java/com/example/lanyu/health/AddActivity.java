package com.example.lanyu.health;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.lanyu.health.base.BaseActivity;
import com.example.lanyu.health.net.Http_utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by lanyu on 2018/12/27.
 */
public class AddActivity extends BaseActivity implements View.OnClickListener {
    private Button mornrunStartBtn,mornrunEndBtn,
                    exerStartBtn,exerEndBtn,
                    walkStartBtn,walkEndBtn,
                    rideStartBtn,rideEndBtn,
                    swimStartBtn,swimEndBtn,
                    ballStartBtn,ballEndBtn,
                    evenStartBtn,evenEndBtn,
                    submitBtn,cancelBtn;
    private EditText distanceEdit,rideEdit,evenDistanceEdit;
    private Calendar calendar;
    private TimePickerDialog timePickerDialog;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        calendar = Calendar.getInstance();
        initView();
        mornrunStartBtn.setOnClickListener(this);
        mornrunEndBtn.setOnClickListener(this);
        exerStartBtn.setOnClickListener(this);
        exerEndBtn.setOnClickListener(this);
        walkStartBtn.setOnClickListener(this);
        walkEndBtn.setOnClickListener(this);
        rideStartBtn.setOnClickListener(this);
        rideEndBtn.setOnClickListener(this);
        swimStartBtn.setOnClickListener(this);
        swimEndBtn.setOnClickListener(this);
        ballStartBtn.setOnClickListener(this);
        ballEndBtn.setOnClickListener(this);
        evenStartBtn.setOnClickListener(this);
        evenEndBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }


    private void initView(){
        mornrunStartBtn = (Button)findViewById(R.id.mornrun_start_btn);
        mornrunEndBtn = (Button)findViewById(R.id.mornrun_end_btn);
        exerStartBtn = (Button)findViewById(R.id.exercise_start_btn);
        exerEndBtn = (Button)findViewById(R.id.exercise_end_btn);
        walkStartBtn = (Button)findViewById(R.id.walk_start_btn);
        walkEndBtn = (Button)findViewById(R.id.walk_end_btn);
        rideStartBtn = (Button)findViewById(R.id.ride_start_btn);
        rideEndBtn = (Button)findViewById(R.id.ride_end_btn);
        swimStartBtn = (Button)findViewById(R.id.swim_start_btn);
        swimEndBtn = (Button)findViewById(R.id.swim_end_btn);
        ballStartBtn = (Button)findViewById(R.id.ball_start_btn);
        ballEndBtn = (Button)findViewById(R.id.ball_end_btn);
        evenStartBtn = (Button)findViewById(R.id.evenrun_start_btn);
        evenEndBtn = (Button)findViewById(R.id.evenrun_end_btn);
        submitBtn = (Button)findViewById(R.id.add_submit_btn);
        cancelBtn = (Button)findViewById(R.id.add_cancel_btn);
        distanceEdit = (EditText)findViewById(R.id.run_distance);
        evenDistanceEdit = (EditText)findViewById(R.id.evenrun_distance);
        rideEdit = (EditText)findViewById(R.id.ride_distance);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            /*case R.id.mornrun_start_btn:
                //晨跑开始时间
            case R.id.mornrun_end_btn:
            case R.id.exercise_start_btn:
            case R.id.exercise_end_btn:
            case R.id.walk_start_btn:
            case R.id.walk_end_btn:
            case R.id.ride_start_btn:
            case R.id.ride_end_btn:
            case R.id.swim_start_btn:
            case R.id.swim_end_btn:
            case R.id.ball_start_btn:
            case R.id.ball_end_btn:
            case R.id.evenrun_start_btn:
            case R.id.evenrun_end_btn:*/
            default:
                timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        ((Button)v).setText(hourOfDay + ":" + minute);
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
                timePickerDialog.show();
                break;
            case R.id.add_submit_btn:
                if(mornrunStartBtn.getText().toString().trim().matches("[\\u4e00-\\u9fa5]*")){
                    toast("请选择开始时间");
                    return;
                }else if(mornrunEndBtn.getText().toString().trim().matches("[\\u4e00-\\u9fa5]*")){
                    toast("请选择结束时间");
                    return;
                }else if(TextUtils.isEmpty(distanceEdit.getText())){
                    toast("请选择里程数");
                    return;
                }else{
                    insertData();
                }
                break;
            case R.id.add_cancel_btn:
                finish();
                break;
        }
    }
    //提交网络数据
    private void insertData(){
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                /*String today = calendar.get(Calendar.YEAR)+"-"+
                        (calendar.get(Calendar.MONTH)+1)+"-"+
                        calendar.get(Calendar.DAY_OF_MONTH)+" ";
                */
                HashMap<String,Object> map = new HashMap<>();
                map.put("sport_name", "晨跑");
                map.put("start_time", mornrunStartBtn.getText().toString());
                map.put("end_time", mornrunEndBtn.getText().toString());
                map.put("account", AppApplication.account);
                map.put("distance", distanceEdit.getText().toString().trim());
                final String result = Http_utils.doPost("add_record.aspx",map);
                Log.e("TAG","==========="+result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if(jsonObject.getInt("code")==0){
                                //添加成功
                                toast("添加成功");
                                finish();
                            }else{
                                //添加失败
                                toast("添加失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        };
        //启动线程
        new Thread(runnable).start();
    }
}
