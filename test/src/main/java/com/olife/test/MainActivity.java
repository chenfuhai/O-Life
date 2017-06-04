package com.olife.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv= (TextView) findViewById(R.id.text);

        String JSON ="{'abc6':'123','abc5':'123','abc4':'123','abc3':'123','abc2':'123','abc1':'123'}";
        HttpUntils.getInstance().postwithJSON(NetConfig.TestAction, JSON,
                new HttpUntils.SuccessListener() {
                    @Override
                    public void onSuccessResponse(String result) {
                        tv.setText(result);
                    }
                }, new HttpUntils.FailedListener() {
                    @Override
                    public void onFialed(int connectCode) {
                        tv.setText(connectCode);
                    }
                }
        );
    }
}
