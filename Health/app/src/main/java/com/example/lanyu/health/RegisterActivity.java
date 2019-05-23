package com.example.lanyu.health;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lanyu.health.base.BaseActivity;
import com.example.lanyu.health.net.Http_utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by lanyu on 2018/12/24.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText accountEdit,passwordEdit, repwdEdit,nameEdit;
    private Button regBtn,birthBtn, backBtn;
    private RadioButton radMan,radWoman;
    private Calendar calendar;
    private RadioGroup rg;
    private String sex = "男";
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        //注册监听事件
        backBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);
        birthBtn.setOnClickListener(this);
        //获取日期对象
        calendar =Calendar.getInstance();
        birthBtn.setText(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_man){
                    sex = "男";
                }else {
                    sex = "女";
                }

            }
        });
    }




    protected void initView(){
        accountEdit = (EditText)findViewById(R.id.reg_account);
        nameEdit =(EditText)findViewById(R.id.reg_name);
        passwordEdit = (EditText)findViewById(R.id.reg_password);
        repwdEdit = (EditText)findViewById(R.id.reg_definepassword);
       // sexEdit = (EditText)findViewById(R.id.reg_sex);
        radMan = (RadioButton)findViewById(R.id.radio_man);
        radWoman = (RadioButton)findViewById(R.id.radio_woman);

        birthBtn = (Button)findViewById(R.id.reg_birthday_btn);
        backBtn = (Button)findViewById(R.id.reg_back_btn);
        regBtn = (Button)findViewById(R.id.reg_btn);
        rg = (RadioGroup)findViewById(R.id.rg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.reg_back_btn:
                //返回
                startIntent(LoginActivity.class);
                finish();
                break;

            case R.id.reg_birthday_btn:
                //生日
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthBtn.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.reg_btn:
                //确定注册
                if(TextUtils.isEmpty(accountEdit.getText())){
                    toast("账号不能为空");
                    return;
                }
                else if(TextUtils.isEmpty(nameEdit.getText())){
                    toast("姓名不能为空");
                    return;
                }
                else if(TextUtils.isEmpty(passwordEdit.getText())){
                    toast("密码不能为空");
                    return;
                }else if(!passwordEdit.getText().toString().trim().equals(repwdEdit.getText().toString().trim())){
                    toast("两次密码不一致");
                    return;
                }else{
                    //提交注册信息
                    register();
                }
                break;
        }

    }
    private void register(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HashMap<String,Object> map = new HashMap<>();
                map.put("account", accountEdit.getText().toString().trim());
                map.put("password",passwordEdit.getText().toString().trim());
                //if(radMan.isChecked())   map.put("sex",radMan.getText().toString());
                map.put("sex",sex);
                // else    map.put("sex",radWoman.getText().toString());
                map.put("birthday",birthBtn.getText().toString().trim());
                map.put("name", nameEdit.getText().toString().trim());
                final String result = Http_utils.doPost("regist.aspx",map);
                Log.e("TAG", "=========" + result);
                if(result!=null){
                    //运行在主线程
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if(jsonObject.getInt("code")==0){
                                    //注册成功
                                    toast("注册成功");
                                    finish();
                                }else{
                                    //注册失败
                                    toast(jsonObject.getString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }
}
