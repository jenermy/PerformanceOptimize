package com.example.performanceoptimize;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class DBFlowActivity extends AppCompatActivity {
    private ListView dbflowList;
    private PeopleAdapter peopleAdapter;
    private Button transactionBtn;
    private Button insertDBFlowBtn,deleteDBFlowBtn,updateDBFlowBtn,queryDBFlowBtn,queryOneBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbflow);
        dbflowList = (ListView)findViewById(R.id.dbflowList);
        insertDBFlowBtn = (Button)findViewById(R.id.insertDBFlowBtn);
        deleteDBFlowBtn = (Button)findViewById(R.id.deleteDBFlowBtn);
        updateDBFlowBtn = (Button)findViewById(R.id.updateDBFlowBtn);
        queryDBFlowBtn = (Button)findViewById(R.id.queryDBFlowBtn);
        queryOneBtn = (Button)findViewById(R.id.queryOneBtn);
        transactionBtn = (Button)findViewById(R.id.transactionBtn);
        insertDBFlowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                People people = new People();
                people.name = "黄景瑜";
                people.gender = 1;
                people.insert();
                people = new People();
                people.name = "AngleBaby";
                people.gender = 2;
                people.insert();
                people = new People();
                people.name = "王俊凯";
                people.gender = 1;
                people.insert();
                people = new People();
                people.name = "佛系少女";
                people.gender = 2;
                people.insert();
            }
        });
        deleteDBFlowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                People people = new People();
                people.name = "佛系少女";
                people.gender = 2;
                people.delete();
            }
        });
        updateDBFlowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                People people = new People();
                people.name = "每天都是星期七";
                people.gender = 2;
                people.update();
            }
        });
        queryDBFlowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<People> peoples = new Select().from(People.class).queryList();
                peopleAdapter = new PeopleAdapter(DBFlowActivity.this,peoples);
                dbflowList.setAdapter(peopleAdapter);
            }
        });
        queryOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<People> peoples = new Select().from(People.class).where(People_Table.gender.eq(1)).queryList();
               StringBuilder sb = new StringBuilder();
                for(int i= 0;i<peoples.size();i++){
                    sb.append(peoples.get(i).name);
                    sb.append(" ");
                }
                Log.i("wanlijun",sb.toString());
            }
        });
        transactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<People> peopleList = new ArrayList<>();
                for(int i = 0;i<10000;i++){
                    People people = new People();
                    people.name = "轻松熊"+i;
                    people.gender = 1;
                    peopleList.add(people);
                }
                new SaveModelTransaction<>(ProcessModelInfo.withModels(peopleList)).onExecute();
            }
        });

    }
    static class PeopleAdapter extends BaseAdapter{
        private Context mContext;
        private List<People> peopleList;
        public PeopleAdapter(Context context,List<People> peopleList){
            this.mContext = context;
            this.peopleList = peopleList;
        }
        @Override
        public int getCount() {
            if(peopleList != null){
                return peopleList.size();
            }else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            if(peopleList != null){
                return peopleList.get(position);
            }else {
                return null;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           convertView = LayoutInflater.from(mContext).inflate(R.layout.dbflow_item_layout,null);
            TextView name = (TextView)convertView.findViewById(R.id.name);
            TextView gender = (TextView)convertView.findViewById(R.id.gender);
            People people = peopleList.get(position);
            name.setText(people.name);
            if(people.gender == 1){
                gender.setText("male");
            }else{
                gender.setText("female");
            }
           return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
