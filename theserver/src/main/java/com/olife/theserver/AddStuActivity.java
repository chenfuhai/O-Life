package com.olife.theserver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static com.olife.theserver.R.id.result;

public class AddStuActivity extends AppCompatActivity {

    private String stuNo,stuName,stuGrade,stuClass,stuCollege,stuSex,stuStatus;
    private EditText etNo,etName,etClass;
    private Spinner spGrade,spCollege;
    private RadioGroup rgSex,rgStatus;

    private Button btnAdd,btnCancel;

    private String result;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstu);


        handler = new Handler();
        etNo = (EditText) findViewById(R.id.stuEditText01);
        etName = (EditText) findViewById(R.id.stuEditText02);
        etClass = (EditText) findViewById(R.id.stuEditText03);

        spGrade = (Spinner) findViewById(R.id.gradeSpinner);
        spCollege = (Spinner) findViewById(R.id.collegeSpinner);

        rgSex = (RadioGroup) findViewById(R.id.sexRg);
        rgStatus = (RadioGroup) findViewById(R.id.statusRg);

        btnAdd = (Button) findViewById(R.id.addStuButton01);
        btnCancel = (Button) findViewById(R.id.addStuButton02);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stuNo = etNo.getEditableText().toString().trim();
                stuName = etName.getEditableText().toString().trim();
                stuClass = etClass.getEditableText().toString().trim();
                stuGrade = spGrade.getSelectedItem().toString().trim();
                stuCollege = spCollege.getSelectedItem().toString().trim();
                switch (rgSex.getCheckedRadioButtonId()){
                    case R.id.maleRadio:
                        stuSex = "男";
                        break;
                    case R.id.femaleRadio:
                        stuSex = "女";
                        break;
                }
                switch (rgStatus.getCheckedRadioButtonId()){
                    case R.id.inRadio:
                        stuStatus = "1";
                        break;
                    case R.id.outRadio:
                        stuStatus = "0";
                        break;
                }

                Log.i("fuhai", "onClick: "+stuNo+
                stuName+stuClass+stuGrade+stuStatus+stuSex+stuCollege);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String target = Config.getServerUrl() + "addStu.action";
                        Log.i("fuhai", "run: target"+target);
                        BasicNameValue basicNameValue1 = new BasicNameValue("sno",stuNo);
                        BasicNameValue basicNameValue2 = new BasicNameValue("sname",stuName);
                        BasicNameValue basicNameValue3 = new BasicNameValue("sclass",stuClass);
                        BasicNameValue basicNameValue4 = new BasicNameValue("scollege",stuCollege);
                        BasicNameValue basicNameValue5 = new BasicNameValue("sgrade",stuGrade);
                        BasicNameValue basicNameValue6 = new BasicNameValue("ssex",stuSex);
                        BasicNameValue basicNameValue7 = new BasicNameValue("sstatus",stuStatus);
                        ArrayList<BasicNameValue> params = new ArrayList<BasicNameValue>();
                        params.add(basicNameValue1);
                        params.add(basicNameValue2);
                        params.add(basicNameValue3);
                        params.add(basicNameValue4);
                        params.add(basicNameValue5);
                        params.add(basicNameValue6);
                        params.add(basicNameValue7);

                        result = HttpUploadUtil.postWithParams(target,params);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (result.equals("success")){
                                    Toast.makeText(AddStuActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
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
                finish();
            }
        });
    }


}
