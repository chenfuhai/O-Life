package com.olife.theserver;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.attr.handle;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.olife.theserver.R.id.result;

public class UpdateStuActivity extends AppCompatActivity {


    private String stuNo,stuName,stuGrade,stuClass,stuCollege,stuSex,stuStatus;
    private EditText etName,etClass;
    private TextView tvNo;
    private Spinner spGrade,spCollege;
    private RadioGroup rgSex,rgStatus;

    private Button btnAdd,btnCancel;

    private String result;
    private Handler handler;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stu);

        stuNo = getIntent().getStringExtra("sno");
        if (stuNo == null || stuNo.equals("")){
            finish();
        }

        handler = new Handler();
        tvNo = (TextView) findViewById(R.id.stuEditText01);
        etName = (EditText) findViewById(R.id.stuEditText02);
        etClass = (EditText) findViewById(R.id.stuEditText03);

        spGrade = (Spinner) findViewById(R.id.gradeSpinner);
        spCollege = (Spinner) findViewById(R.id.collegeSpinner);

        rgSex = (RadioGroup) findViewById(R.id.sexRg);
        rgStatus = (RadioGroup) findViewById(R.id.statusRg);

        btnAdd = (Button) findViewById(R.id.addStuButton01);
        btnCancel = (Button) findViewById(R.id.addStuButton02);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String target = Config.getServerUrl() + "getStuData.action";
                     student = HttpUploadUtil.getStudentByID(target,stuNo);
                if (student!=null){
                   stuNo = student.getSno();
                    stuName = student.getSname();
                    stuClass = student.getSclass();
                    stuCollege = student.getScollege();
                    stuGrade = student.getSgrade();
                    stuSex = student.getSsex();

                    stuStatus = student.getSstatus()+"";

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvNo.setText(stuNo);
                            etName.setText(stuName);
                            etClass.setText(stuName);

                            switch (stuGrade){
                                case "一年级":
                                    spGrade.setSelection(0);
                                    break;
                                case "二年级":
                                    spGrade.setSelection(1);
                                    break;
                                case "三年级":
                                    spGrade.setSelection(2);
                                    break;
                                case "四年级":
                                    spGrade.setSelection(3);
                                    break;

                            }

                            switch (stuCollege){
                                case "软件学院":
                                    spCollege.setSelection(0);
                                    break;
                                case "音乐学院":
                                    spCollege.setSelection(1);
                                    break;
                                case "美术学院":
                                    spCollege.setSelection(2);
                                    break;

                                case "数学学院":
                                    spCollege.setSelection(3);
                                    break;

                            }

                            switch (stuSex){
                                case "男" :
                                    rgSex.check(R.id.maleRadio);
                                    break;
                                case "女" :
                                    rgSex.check(R.id.femaleRadio);
                                    break;
                            }

                            switch (stuStatus){
                                case "0" :
                                    rgStatus.check(R.id.inRadio);
                                    break;
                                case "1" :
                                    rgStatus.check(R.id.outRadio);
                                    break;
                            }
                        }
                    });
                }

            }
        }).start();




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                        String target = Config.getServerUrl() + "updateStuData.action";
                        Log.i("fuhai", "run: target"+target);
                        BasicNameValue basicNameValue1 = new BasicNameValue("sno",stuNo);
                        BasicNameValue basicNameValue2 = new BasicNameValue("name",stuName);
                        BasicNameValue basicNameValue3 = new BasicNameValue("class",stuClass);
                        BasicNameValue basicNameValue4 = new BasicNameValue("college",stuCollege);
                        BasicNameValue basicNameValue5 = new BasicNameValue("grade",stuGrade);
                        BasicNameValue basicNameValue6 = new BasicNameValue("sex",stuSex);
                        BasicNameValue basicNameValue7 = new BasicNameValue("status",stuStatus);
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
                                    Toast.makeText(UpdateStuActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
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
