package com.olife.o_life;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.olife.o_life.biz.OnekeySharedMessageBiz;
import com.olife.o_life.bizImpl.OnekeySharedMessageBizImpl;
import com.olife.o_life.entity.OnekeySharedMessage;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView textView2 = (TextView) findViewById(R.id.textView6);
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OnekeySharedMessageBizImpl().findOthersSharedByLatLng(28.680625, 116.036220, new OnekeySharedMessageBiz.FindSharedDoingLisenter() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(List<OnekeySharedMessage> objects) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0;i<objects.size();i++){
                            OnekeySharedMessage temp = objects.get(i);
                            sb.append(temp.getUserId());
                            sb.append("\n");
                            sb.append(temp.getLat());
                            sb.append("\n");
                            sb.append(temp.getLng());
                            sb.append("\n");
                            sb.append(temp.getResultMark());
                            sb.append("\n");
                        }
                        textView2.setText(sb.toString());
                        Log.i("fuhai", "onSuccess: "+sb.toString()+objects.size());
                    }

                    @Override
                    public void onFailed(int e) {
                       // BmobError.showErrorMessage(getApplicationContext(),e);
                    }
                });


            }
        });


    }
}
