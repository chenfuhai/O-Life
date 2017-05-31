package com.olife.theserver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManagerStuActivity extends AppCompatActivity {

    private HomeAdapter myAdaper;
    private ArrayList<Student> students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_stu);

        students = (ArrayList<Student>) getIntent().getSerializableExtra("students");
        if (students==null){
            finish();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(myAdaper = new HomeAdapter());

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.HORIZONTAL));

        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });

    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    ManagerStuActivity.this).inflate(R.layout.manager_stu_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tvSname.setText(students.get(position).getSname());
            holder.tvSno.setText(students.get(position).getSno());
            holder.tvSclass.setText(students.get(position).getSclass());
            holder.tvSclloge.setText(students.get(position).getScollege());
            holder.position = position;
        }

        @Override
        public int getItemCount()
        {
            return students.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tvSno,tvSname,tvSclass,tvSclloge;
            ImageView img;
            int position;
            private Handler handler;

            public MyViewHolder(View view)
            {
                super(view);
                handler = new Handler();
                tvSno = (TextView) view.findViewById(R.id.stuSno);
                tvSname = (TextView) view.findViewById(R.id.stuSname);
                tvSclass = (TextView) view.findViewById(R.id.stuSclass);
                tvSclloge = (TextView) view.findViewById(R.id.stuScollege);
                img = (ImageView) view.findViewById(R.id.stuImg);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sno = students.get(position).getSno();
                        Intent intent = new Intent(getApplicationContext(),UpdateStuActivity.class);
                        intent.putExtra("sno",sno);
                        startActivity(intent);
                        finish();
                    }
                });
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                       String sno = students.get(position).getSno();
                        BasicNameValue ba = new BasicNameValue("stuSno",sno);
                        final ArrayList<BasicNameValue> al = new ArrayList<BasicNameValue>();
                        al.add(ba);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final String  flag =HttpUploadUtil.postWithParams(Config.getServerUrl()+"delStu.action",al);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (flag.equals("success")){
                                            Toast.makeText(ManagerStuActivity.this, "删除"+students.get(position).getSname(), Toast.LENGTH_SHORT).show();
                                            students.remove(position);
                                            HomeAdapter.this.notifyDataSetChanged();
                                        }else{
                                            Toast.makeText(ManagerStuActivity.this, "删除失败 请检查网络", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }).start();

                        return false;
                    }
                });
            }
        }
    }


}
