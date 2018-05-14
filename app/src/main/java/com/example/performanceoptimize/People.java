package com.example.performanceoptimize;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * @author wanlijun
 * @description
 * @time 2018/5/14 9:31
 */
@ModelContainer
@Table(database = APPDatabase.class)
public class People extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public String name;
    @Column
    public int gender;
}
