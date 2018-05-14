package com.example.performanceoptimize;

/**
 * @author wanlijun
 * @description
 * @time 2018/5/9 16:39
 */

public class CupInfo {
    public long mId = 0; //一个CPU信息的id
    public long mCpuRate = 0; //总的CPU使用率
    public long mAppRate = 0; //当前APP的CPU使用率
    public long mUserRate = 0; //用户进程
    public long mSystemRate = 0; //系统进程
    public long mIoWait = 0; //等待时间

    @Override
    public String toString() {
        return "mCpuRate="+mCpuRate+"\n"
                +"mAppRate="+mAppRate+"\n"
                +"mUserRate="+mUserRate+"\n"
                +"mSystemRate="+mSystemRate+"\n"
                +"mIoWait="+mIoWait;
    }
}
