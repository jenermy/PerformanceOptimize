package com.example.performanceoptimize;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.util.Log;

import com.example.greendao.DaoMaster;
import com.example.greendao.DaoSession;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author wanlijun
 * @description
 * @time 2018/5/11 16:38
 */

public class MyApplication extends Application {
    public DaoMaster daoMaster;
    public DaoSession daoSession;
    public SQLiteDatabase db;
//    private static class ApplicationHolder{
//        private static final MyApplication instance = new MyApplication();
//    }
    @Override
    public void onCreate() {
        super.onCreate();
        // 官方推荐将获取 DaoMaster 对象的方法放到 Application 层，这样将避免多次创建生成 Session 对象
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"notes-db");
        db = helper.getWritableDatabase();
        //该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        Log.i("wanlijun",daoSession.toString());
        FlowManager.init(this);
        if(BuildConfig.DEBUG){
            LeakCanary.install(this); //内存分析
        }
    }
//    private MyApplication(){}
//    public static final MyApplication getInstance(){
//        return ApplicationHolder.instance;
//    }
}
