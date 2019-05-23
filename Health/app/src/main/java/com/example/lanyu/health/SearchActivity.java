package com.example.lanyu.health;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lanyu.health.base.BaseActivity;
import com.example.lanyu.health.net.Http_utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private Button searchStartBtn, searchEndBtn,cancelBtn,submitBtn;
    private ListView listView;
    private TextView tv;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private String sportName;
    private List<JSONObject> sportDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        calendar = Calendar.getInstance();
        searchStartBtn.setOnClickListener(this);
        searchEndBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        sportName = getIntent().getStringExtra("name");
        tv.setText(sportName);



    }

    private void initView(){
        searchStartBtn = (Button)findViewById(R.id.search_start_btn);
        searchEndBtn = (Button)findViewById(R.id.search_end_btn);
        cancelBtn = (Button)findViewById(R.id.search_cancel_btn);
        submitBtn = (Button)findViewById(R.id.search_submit_btn);
        listView = (ListView)findViewById(R.id.listView);
        tv = (TextView)findViewById(R.id.search_title_tv);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.search_start_btn:
            case R.id.search_end_btn:
                //年月日
                datePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ((Button)v).setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;

            case    R.id.search_cancel_btn:
                finish();
                break;
            case    R.id.search_submit_btn:
                getData();
                break;

        }
    }

    private void getData(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HashMap<String,Object> map= new HashMap<>();
                map.put("account",AppApplication.account);
                map.put("sport_name",sportName);
                map.put("start_time", "");
                map.put("end_time", "");

                final String result = Http_utils.doPost("get_records.aspx",map);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // toast(result);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            if(jsonObject.getInt("code") == 0){
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for(int i=0;i<jsonArray.length();i++){
                                        sportDatas.add(jsonArray.getJSONObject(i));
                                    }
                                }
                            else{
                                toast(jsonObject.getString("msg"));
                            }
                            listView.setAdapter(new MyAdapter());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });

            }
        };
        new Thread(runnable).start();
    }

    private class MyAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return sportDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.listview_item,parent,false);
            TextView startTv = (TextView)convertView.findViewById(R.id.item_start);
            TextView endTv = (TextView)convertView.findViewById(R.id.item_end);
            TextView mileTv = (TextView)convertView.findViewById(R.id.item_mileage);
            try {
                startTv.setText(sportDatas.get(position).getString("start_time"));
                endTv.setText(sportDatas.get(position).getString("end_time"));
                mileTv.setText(sportDatas.get(position).getString("distance"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }





}



