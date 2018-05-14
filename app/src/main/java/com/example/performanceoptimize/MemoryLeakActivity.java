package com.example.performanceoptimize;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MemoryLeakActivity extends AppCompatActivity {
    private TextView textTv;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leak);
        textTv = (TextView)findViewById(R.id.textTv);
        //匿名内部类Runnable对Textview有引用,TextView对Activity有引用，当Activity被销毁时（旋转或者关闭），由于这些引用关系，activity不能被及时回收，造成内存泄露
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textTv.setText("我失了忆，每天都是星期七");
            }
        },80000L);
    }
}
