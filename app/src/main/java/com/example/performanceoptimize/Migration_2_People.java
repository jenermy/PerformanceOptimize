package com.example.performanceoptimize;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * @author wanlijun
 * @description
 * @time 2018/5/14 10:29
 */
//数据库升级步骤：
//第1步，修改数据库版本号
//第2步，需要修改数据表对象结构，增加email
//第3步，执行第二步之后，需要build(Android studio的build->Make Project、Mac的童鞋直接command+F9)，通过apt更新People_Table，接下来编写Migrations
@Migration(version = 2,database = APPDatabase.class)
public class Migration_2_People extends AlterTableMigration<People> {
    public Migration_2_People(Class<People> table){
        super(table);
    }

    @Override
    public void onPreMigrate() {
//        addColumn(SQLiteType.TEXT, People_Table.email.getNameAlias().getName());//更新数据库，给people表添加email字段
    }
}
