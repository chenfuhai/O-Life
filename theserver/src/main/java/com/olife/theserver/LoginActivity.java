package com.olife.theserver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnCancel;
    private EditText tvName, tvPwd,etIp;
    private Handler handler;
    private String result = "none";
    private String name;
    private String pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }

    private void init() {
        this.btnLogin = (Button) findViewById(R.id.loginButton01);
        this.btnCancel = (Button) findViewById(R.id.loginButton02);
        this.tvName = (EditText) findViewById(R.id.loginEditText01);
        this.tvPwd = (EditText) findViewById(R.id.loginEditText02);
        this.etIp = (EditText) findViewById(R.id.et_ip);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                System.out.println(result);
                System.out.println((String)msg.obj);
                Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
            }
        };
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etIp.getEditableText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"请输入连接IP",Toast.LENGTH_SHORT).show();
                    return;
                }
                Config.SERVER_IP = etIp.getEditableText().toString().trim();
                name = tvName.getEditableText().toString().trim();
                pwd = tvPwd.getEditableText().toString().trim();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String target = Config.getServerUrl() + "loginuser.action";
                        BasicNameValue basicNameValue1 = new BasicNameValue("username",name);
                        BasicNameValue basicNameValue2 = new BasicNameValue("password",pwd);
                        ArrayList<BasicNameValue> params = new ArrayList<BasicNameValue>();
                        params.add(basicNameValue1);
                        params.add(basicNameValue2);

                            result = HttpUploadUtil.postWithParams(target,params);

                        Message m = new Message();
                        m.obj = result;
                        handler.sendMessage(m);

                       handler.post(new Runnable() {
                           @Override
                           public void run() {
                               if (result.equals("success")){
                                   startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                   finish();
                               }
                           }
                       });

                    }
                }).start();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvName.setText("");
                tvPwd.setText("");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "https://ajax.googleapis.com/ajax/" +
                                "services/search/web?v=1.0&q={query}";

// Create a new RestTemplate instance
                        RestTemplate restTemplate = new RestTemplate();

// Add the String message converter
                        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

// Make the HTTP GET request, marshaling the response to a String
                        String result = restTemplate.getForObject(url, String.class, "Android");

                        Log.i("fuhai", "onClick: "+result);
                    }
                }).start();



            }
        });
    }


}
