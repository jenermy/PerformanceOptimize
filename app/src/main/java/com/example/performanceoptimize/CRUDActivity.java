package com.example.performanceoptimize;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.example.greendao.Note;
import com.example.greendao.NoteDao;
import java.util.Date;

public class CRUDActivity extends AppCompatActivity {

    private Button queryBtn,updateBtn,deleteBtn,insertBtn;
    private ListView dbDataList;
    SimpleCursorAdapter simpleAdapter;
    private Cursor cursor;
    private MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        myApplication = (MyApplication) getApplication();
        insertBtn = findViewById(R.id.insertBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        updateBtn = findViewById(R.id.updateBtn);
        queryBtn = findViewById(R.id.queryBtn);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNote("haha","hah");
//                cursor = myApplication.db.query(getNoteDao().getTablename(),getNoteDao().getAllColumns(),null,null,null,null,null);
//                simpleAdapter.notifyDataSetChanged(); //这个刷新列表无效
                cursor.requery(); //用来刷新列表
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor = myApplication.db.query(getNoteDao().getTablename(),getNoteDao().getAllColumns(),null,null,null,null,null);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        dbDataList = findViewById(R.id.dbDataList);
        insertNote("快乐大本营","天天好心情");
        insertNote("好好学习","天天向上");
        insertNote("王牌对王牌","不服你就来");
        insertNote("向往的生活","哈哈哈");
        insertNote("体面","谁都不要说抱歉");
        cursor = myApplication.db.query(getNoteDao().getTablename(),getNoteDao().getAllColumns(),null,null,null,null,null);
        Log.i("wanlijun",cursor.getCount()+"");
        simpleAdapter = new SimpleCursorAdapter(CRUDActivity.this,R.layout.db_item_layout,cursor,new String[]{NoteDao.Properties.Text.columnName,NoteDao.Properties.Content.columnName, NoteDao.Properties.Date.columnName},new int[]{R.id.text,R.id.content,R.id.date});
        dbDataList.setAdapter(simpleAdapter);
    }

    private NoteDao getNoteDao() {
        return myApplication.daoSession.getNoteDao();
    }
    private void insertNote(String text,String content){
        Note note = new Note(null,text,content,new Date());
        getNoteDao().insert(note);
//        cursor.requery();
    }
    private void deleteNote(){
//        getNoteDao().deleteByKey(id);
    }
    private void updateNote(){

    }
    private Cursor queryNote(){
        return myApplication.db.query(getNoteDao().getTablename(),getNoteDao().getAllColumns(),null,null,null,null,null);
    }
}
