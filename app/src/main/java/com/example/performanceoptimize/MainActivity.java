package com.example.performanceoptimize;

import android.Manifest;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView nameList;
    private String[] nameAry;
    private ArrayList<Map<String,Object>> datas;
    private ImageView animationIv;
    private ImageView propertyAnimationIv;
    private Button monitorBtn;
    private Button stopBtn;
    private Button loopBtn;
    private Button nolooperBtn;
    private Button greendaoBtn;
    private Button dbflowBtn;
    private Button memoryLeakBtn;
    private TextView appHeapSizeTv;
    private Handler handler;
    private int[] images = {R.drawable.junjie6,
       R.drawable.junjie8,
       R.drawable.dog6,
       R.drawable.dog7};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_main);
        appHeapSizeTv = (TextView)findViewById(R.id.appHeapSizeTv);
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        appHeapSizeTv.setText(activityManager.getMemoryClass()+""); //当前应用的heap size阈值
        final CupInfoSampler cupInfoSampler = new CupInfoSampler();
        nameList = (ListView)findViewById(R.id.nameList);
        nameAry = getResources().getStringArray(R.array.takeaway);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,nameAry);
//        nameList.setAdapter(adapter);
        datas =  new ArrayList<>();
        for (int i=0;i<nameAry.length;i++){
            Map<String,Object> temp = new HashMap<>();
            temp.put("name",nameAry[i]);
            temp.put("pic",images[i]);
            datas.add(temp);
        }
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,datas,R.layout.item_layout,new String[]{"pic","name"},new int[]{R.id.iconIv,R.id.nameTv});
        nameList.setAdapter(adapter);
//        animationIv = (ImageView)findViewById(R.id.animationIv);
//        RotateAnimation rotateAnimation = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        AnimationSet animationSet = new AnimationSet(true);
//        animationSet.addAnimation(rotateAnimation);
//        animationSet.addAnimation(scaleAnimation);
//        animationSet.setDuration(3000);
//        animationIv.startAnimation(animationSet);

        propertyAnimationIv = (ImageView)findViewById(R.id.propertyAnimationIv);
//        ObjectAnimator scalex = ObjectAnimator.ofFloat(propertyAnimationIv,"scaleX",0,1);
//        ObjectAnimator scaley = ObjectAnimator.ofFloat(propertyAnimationIv,"scaleY",0,1);
//        ObjectAnimator rotation = ObjectAnimator.ofFloat(propertyAnimationIv,"rotation",0.0f,360f);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.setDuration(3000);
//        animatorSet.play(scalex).with(scaley).with(rotation);
//        animatorSet.start();

        monitorBtn = (Button)findViewById(R.id.monitorBtn);
        monitorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Looper.getMainLooper().setMessageLogging(new LogPrinter(new LogPrinter.LogPrinterListener() {
                    @Override
                    public void onStartLoop() {
                        cupInfoSampler.start();
                    }

                    @Override
                    public void onEndLoop() {
                        Log.i("wanlijun","onEndLoop");
                        //只在开始和结束的时候才会执行
                    }
                }));
            }
        });
        stopBtn = (Button)findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Looper.getMainLooper().setMessageLogging(null);
                cupInfoSampler.stop();
            }
        });
        loopBtn = (Button)findViewById(R.id.loopBtn);
        loopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyThread myThread = new MyThread();
                myThread.start();
            }
        });
        nolooperBtn = (Button)findViewById(R.id.nolooperBtn);
        nolooperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyThreadNoHandler myThreadNoHandler = new MyThreadNoHandler();
                myThreadNoHandler.start();
            }
        });
        greendaoBtn = (Button)findViewById(R.id.greendaoBtn);
        greendaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CRUDActivity.class));
            }
        });
        dbflowBtn = (Button)findViewById(R.id.dbflowBtn);
        dbflowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DBFlowActivity.class));
            }
        });
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
        }
        memoryLeakBtn = (Button)findViewById(R.id.memoryLeakBtn);
        memoryLeakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MemoryLeakActivity.class));
            }
        });
    }
    class NameAdapter extends BaseAdapter{
        String[] nameArray;
        Context mContext;
        public NameAdapter(String[] nameAry,Context context){
            this.nameArray = nameAry;
            this.mContext = context;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return null;
        }

        @Override
        public Object getItem(int position) {
            if(nameArray != null){
                return nameArray[position];
            }else{
                return  null;
            }
        }

        @Override
        public int getCount() {
            if(nameArray != null){
                return nameArray.length;
            }else{
                return 0;
            }
        }
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            //如果没有调用Looper.prepare()这句会报错：can't create handler inside thread without looper
            //子线程不会自动开启消息循环，需要调用Looper.prepare();开启
            Looper.prepare();
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Log.i("wanlijun",msg.what +":");
                    System.out.println(msg.obj);
                }
            };
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
                Log.i("wanlijun",e.toString());
            }
            Message message = Message.obtain();
            message.what = 1;
            message.obj = "havana";
            handler.sendMessage(message);
            //如果没有调用Looper.loop();Looper不会开始工作，不会处理消息
            //调用Looper.loop()后才会从消息队列里取消息，处理消息，
            // Looper.loop()后面的代码不会被执行，除非调用了handler.getLooper.quit()Looper才会被终止，后面的代码才会被执行
            Looper.loop();
        }
    }
    class MyThreadNoHandler extends  Thread{
        @Override
        public void run() {
            //没有handler就可以不用Looper了
            Log.i("wanlijun","no handler no looper");
        }
    }
}
