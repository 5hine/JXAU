package com.example.lanyu.health;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.lanyu.health.base.BaseActivity;
import com.example.lanyu.health.net.Http_utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by lanyu on 2018/12/24.
 */
public class LoginActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private EditText accountEdit,pwdEdit;
    private Button regBtn,loginBtn;
    private CheckBox rememberpassword;
    private SharedPreferences spf;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();

        regBtn.setOnClickListener(listener);
        loginBtn.setOnClickListener(listener);
        rememberpassword.setOnCheckedChangeListener(this);
        //获取文件存储
        spf = getSharedPreferences("user",MODE_PRIVATE);
    }

    private void initview(){
        accountEdit =(EditText) findViewById(R.id.login_account);
        pwdEdit = (EditText)findViewById(R.id.login_password);
        regBtn = (Button)findViewById(R.id.login_reg_btn);
        loginBtn=(Button)findViewById(R.id.login_login_btn);
        rememberpassword = (CheckBox)findViewById(R.id.login_remember_password);


    }

    private View.OnClickListener listener = new View.OnClickListener(){
        public void onClick(View v){
            switch (v.getId()){
                case R.id.login_login_btn:
                    //登录
                    if(TextUtils.isEmpty(accountEdit.getText())){
                        toast("用户名不能为空");
                        return;
                    }else if(TextUtils.isEmpty(pwdEdit.getText())){
                        toast("密码不能为空");
                        return;
                    }else{
                        //执行登录
                        login();
                    }
                    break;
                case R.id.login_reg_btn:
                    startIntent(RegisterActivity.class);
                    break;
            }
        }
    };

    private void login(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HashMap<String,Object> map = new HashMap<>();
                map.put("account",accountEdit.getText().toString().trim());
                map.put("password",pwdEdit.getText().toString().trim());
                final String result = Http_utils.doPost("login.aspx",map);
                Log.e("TAG","========="+result);
                //运行在主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //主线程
                        //toast(result);
                        JSONObject jsonObject = null;
                        try {
                            //将数据转化成json格式
                            jsonObject = new JSONObject(result);
                            //取出json数据
                            int code = jsonObject.getInt("code");
                            if(code==0){
                                AppApplication.account = accountEdit.getText().toString().trim();
                                startIntent(MainActivity.class);
                                toast("登陆成功");

                            }else{
                                toast(jsonObject.getString("msg"));
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        //获取编辑
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(accountEdit.getText().toString(),pwdEdit.getText().toString());

        if(isChecked){
            //选中
        }else{
            //未选中
        }
    }
}
