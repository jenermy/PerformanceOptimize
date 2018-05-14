package com.example.performanceoptimize;

import android.util.Log;
import android.util.Printer;

/**
 * @author wanlijun
 * @description
 * @time 2018/5/9 15:40
 */

public class LogPrinter implements UiPerfMonitorConfig,Printer {
    private LogPrinterListener logPrinterListener = null;
    private long startTime = 0;
    public LogPrinter(LogPrinterListener logPrinterListener){
        this.logPrinterListener = logPrinterListener;
    }
    @Override
    public void println(String x) {
        if(startTime <= 0){
            startTime = System.currentTimeMillis();
            logPrinterListener.onStartLoop();
        }else{
            long time = System.currentTimeMillis() - startTime;
            Log.i("wanlijun","dispatch handler time:"+time);
            executeTime(x,time);
            startTime = 0;
        }
    }
    private void executeTime(String loginfo,long time){
        int level = 0;
        if(time > TIME_WARNING_LEVEL_2){
            Log.i("wanlijun","TIME_WARNING_LEVEL_2:"+loginfo);
            level = 2;
        }else if(time > TIME_WARNING_LEVEL_1){
            Log.i("wanlijun","TIME_WARNING_LEVEL_1:"+loginfo);
            level = 1;
        }
        logPrinterListener.onEndLoop();
    }
    public interface LogPrinterListener{
        void onStartLoop();
        void onEndLoop();
    }
}
