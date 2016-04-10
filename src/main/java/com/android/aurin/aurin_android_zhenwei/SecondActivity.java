package com.android.aurin.aurin_android_zhenwei;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity{

    ArrayList<Capabilities> cap2 = AllDatastes.lists;
    ArrayList<String> data = new ArrayList<>();


//    String[] data1 = {"aaa","bbb","vvv","www"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        for(int i=0;i<cap2.size();i++){
            String title = cap2.get(i).title;
            data.add(title);
        }

        Intent intent = getIntent();
        if ("action".equals(intent.getAction())){
            BBOX filter_bbox = new BBOX();
            filter_bbox = (BBOX)intent.getSerializableExtra("bbox");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    SecondActivity.this, android.R.layout.simple_list_item_1, data);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(adapter);
            System.out.println(filter_bbox.getHigherLa());
        }


    }
}
