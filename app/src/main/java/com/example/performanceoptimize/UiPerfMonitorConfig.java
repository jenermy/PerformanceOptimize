package com.example.performanceoptimize;

import android.os.Environment;

/**
 * @author wanlijun
 * @description
 * @time 2018/5/9 15:33
 */

public interface UiPerfMonitorConfig {
    int TIME_WARNING_LEVEL_1 = 100;
    int TIME_WARNING_LEVEL_2 = 300;
    String LOG_PATH = Environment.getExternalStorageDirectory().getPath() + "uiperf";
}
