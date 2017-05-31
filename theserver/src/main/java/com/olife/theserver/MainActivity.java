package com.olife.theserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnAdd = (Button) findViewById(R.id.button2);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddStuActivity.class));
            }
        });

        Button btnManager = (Button) findViewById(R.id.button4);
        btnManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        ArrayList<Student> students = null;
                        students = HttpUploadUtil.getALLStudent(Config.getServerUrl()+"update.action");

                        Intent i = new Intent(getApplicationContext(),ManagerStuActivity.class);
                        i.putExtra("students",(Serializable) students);
                        startActivity(i);
                    }
                }).start();
            }
        });
    }


}
