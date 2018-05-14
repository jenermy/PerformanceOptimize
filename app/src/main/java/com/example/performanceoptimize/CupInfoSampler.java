package com.example.performanceoptimize;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author wanlijun
 * @description 采集CPU信息
 * @time 2018/5/9 16:15
 */

public class CupInfoSampler extends BaseSampler {
    private int mPid = -1;
    private ArrayList<CupInfo> cupInfoArrayList = new ArrayList<>();
    private long mUserPre = 0;
    private long mSystemPre = 0;
    private long mIdlePre = 0;
    private long mIoWaitPre = 0;
    private long mTotalPre = 0;
    private long mAppCpuTimePre = 0;

    @Override
    public void start() {
        super.start();
        mUserPre = 0;
        mIdlePre = 0;
        mTotalPre = 0;
        mIoWaitPre = 0;
        mAppCpuTimePre = 0;
        mSystemPre = 0;
        cupInfoArrayList.clear();
    }

    @Override
    void doSample() {
        BufferedReader cpuReader = null;
        BufferedReader pidReader = null;
        try {
            cpuReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")),1024);
            String cpuLine = cpuReader.readLine();
            if(cpuLine == null)cpuLine = "";
            if(mPid < 0){
                mPid = android.os.Process.myPid();
            }
            pidReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/"+mPid+"/stat")),1024);
            String pidRate = pidReader.readLine();
            if(pidRate == null) pidRate = "";
            parseCupRate(cpuLine,pidRate);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(cpuReader != null)cpuReader.close();
                if(pidReader != null)pidReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void parseCupRate(String cpuRate,String pidRate){
        Log.i("wanlijun","cpuRate:"+cpuRate);
//        Log.i("wanlijun","pidRate:"+pidRate);
        String[] cpuArray = cpuRate.split(" ");
        String[] pidArray = pidRate.split(" ");
        if(cpuArray.length < 9)return;
        long user_time = Long.parseLong(cpuArray[2]);
        long nice_time = Long.parseLong(cpuArray[3]);
        long system_time = Long.parseLong(cpuArray[4]);
        long idle_time = Long.parseLong(cpuArray[5]);
        long ioWait_time = Long.parseLong(cpuArray[6]);
        long total_time = user_time + nice_time + system_time + idle_time + ioWait_time
                + Long.parseLong(cpuArray[7]) + Long.parseLong(cpuArray[8]);
        if(pidArray.length < 17) return;
        long appCpu_time = Long.parseLong(pidArray[13]) + Long.parseLong(pidArray[14])
                +Long.parseLong(pidArray[15]) + Long.parseLong(pidArray[16]);
        if(mAppCpuTimePre > 0){
            CupInfo cupInfo = new CupInfo();
            long idleTime = idle_time - mIdlePre;
            long totalTime = total_time - mTotalPre;
            cupInfo.mCpuRate = (totalTime - idleTime) * 100L /totalTime;
            cupInfo.mAppRate = (appCpu_time - mAppCpuTimePre) * 100L / totalTime;
            cupInfo.mSystemRate = (system_time - mSystemPre) * 100L / totalTime;
            cupInfo.mUserRate = (user_time - mUserPre) * 100L / totalTime;
            cupInfo.mIoWait = (ioWait_time - mIoWaitPre) * 100L /totalTime;
            synchronized (cupInfoArrayList){
                cupInfoArrayList.add(cupInfo);
                Log.i("wanlijun",cupInfo.toString());
            }
        }
        mAppCpuTimePre = appCpu_time;
        mIoWaitPre = ioWait_time;
        mUserPre = user_time;
        mSystemPre = system_time;
        mTotalPre = total_time;
        mIdlePre = idle_time;
    }
}
