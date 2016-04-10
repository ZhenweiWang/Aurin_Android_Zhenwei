package com.android.aurin.aurin_android_zhenwei;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity{

    ArrayList<Capabilities> cap2 = AllDatastes.lists;
    ArrayList<String> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
//        for(int i=0;i<cap2.size();i++){
//            String title = cap2.get(i).title;
//            data.add(title);
//        }

        Intent intent = getIntent();
        if ("action".equals(intent.getAction())){
            BBOX filter_bbox = new BBOX();
            filter_bbox = (BBOX)intent.getSerializableExtra("bbox");
            CapAdapter adapter = new CapAdapter(SecondActivity.this, R.layout.list_view_sub, cap2);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(adapter);
            System.out.println(filter_bbox.getHigherLa());

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Capabilities cap = cap2.get(position);
                    Intent intent = new Intent(SecondActivity.this, DetailActivity.class);
                    intent.setAction("action");
                    startActivity(intent);
                }
            });
        }


    }
}
