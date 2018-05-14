package com.example.performanceoptimize;

import android.os.Handler;
import android.os.HandlerThread;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.LogRecord;

/**
 * @author wanlijun
 * @description 定时采样
 * @time 2018/5/9 16:00
 */

public abstract class BaseSampler {
    private Handler mControlHandler = null;
    private int intervalTime = 500; //采样时间间隔
    private AtomicBoolean mIsSample = new AtomicBoolean(false);
    private Handler getmControlHandler(){
        if (null == mControlHandler){
            //HandlerThread自己创建了消息队列，自己开启了消息循环,内部建立Looper
            HandlerThread handlerThread = new HandlerThread("SampleThread");
            handlerThread.start();
            //quit()消息队列不再接收新的消息，清空消息队列所有消息包括延时消息和非延时消息
           // handlerThread.quit();
            //quitSafely()消息队列不再接收新的消息，清空消息队列所有的延时消息，将所有的非延时消息派发出去由handler处理
            //handlerThread.quitSafely();
            //调用了quit()或quitSafely()之后，消息循环终结了，这时再通过handler的sendMessage或者post发送消息将返回false，表示消息加入消息队列失败，因为消息队列已经退出
            mControlHandler = new Handler(handlerThread.getLooper());
        }
        return mControlHandler;
    }
    abstract void doSample();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            doSample();
            if(mIsSample.get()){
                getmControlHandler().postDelayed(runnable,intervalTime);
            }
        }
    };
    public void start(){
        if (!mIsSample.get()){
            getmControlHandler().removeCallbacks(runnable);
            getmControlHandler().post(runnable);
            mIsSample.set(true);
        }
    }
    public void stop(){
        if(mIsSample.get()){
            getmControlHandler().removeCallbacks(runnable);
            mIsSample.set(false);
        }
    }
}
