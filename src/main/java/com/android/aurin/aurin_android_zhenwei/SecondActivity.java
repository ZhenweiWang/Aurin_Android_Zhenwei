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
        Intent intent = getIntent();
        System.out.println("almost finished! ");
        if ("action".equals(intent.getAction())){
            data = (ArrayList<String>) intent.getSerializableExtra("titles");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    SecondActivity.this, android.R.layout.simple_list_item_1, data);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(adapter);
            System.out.println("Finished!");

            for (int i =0; i<cap2.size();i++){
                System.out.println(cap2.get(i).organization);
            }

        }

    }
}
