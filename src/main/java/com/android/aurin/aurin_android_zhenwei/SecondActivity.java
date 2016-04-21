package com.android.aurin.aurin_android_zhenwei;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;


public class SecondActivity extends AppCompatActivity{



    ArrayList<Capabilities> cap2 = AllDatastes.lists;
    ArrayList<String> spinner_Items = new ArrayList<>();
    ArrayAdapter<String> adapter1;
    public Spinner spinner;
    public EditText eSearch;

//    CapAdapter adapter = new CapAdapter(SecondActivity.this, R.layout.list_view_sub, cap2);
//    ListView listView = (ListView) findViewById(R.id.list_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
//        for(int i=0;i<cap2.size();i++){
//            String title = cap2.get(i).title;
//            data.add(title);
//        }
        for(int i =0; i<cap2.size(); i++){
            String org = cap2.get(i).organization;
            if(! spinner_Items.contains(org))
                spinner_Items.add(org);
        }
        spinner_Items.add("All Organizations");

        Intent intent = getIntent();
        if ("action".equals(intent.getAction())){
            BBOX filter_bbox;
            filter_bbox = (BBOX)intent.getSerializableExtra("bbox");
            Picked_City.picked_city = filter_bbox;

            spinner = (Spinner) findViewById(R.id.organization);

            adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,spinner_Items);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter1);
            spinner.setSelection(spinner_Items.size()-1);
            spinner.setOnItemSelectedListener(new SpinnerSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view0, int position, long id) {

                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spinner.setVisibility(View.VISIBLE);

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
                    intent.putExtra("capobj", cap);
                    startActivity(intent);
                }
            });


            ImageButton go;

            eSearch = (EditText) findViewById(R.id.etSearch);


            go = (ImageButton) findViewById(R.id.keyword);
            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String orgselec = spinner.getSelectedItem().toString();
                    final String search = eSearch.getText().toString();
                    ArrayList<String> organdselec = new ArrayList<String>();
                    organdselec.add(orgselec);
                    organdselec.add(search);

                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                    intent.setAction("action");
                    intent.putExtra("organdselec",organdselec);
                    startActivity(intent);
//                    boolean flag = true;
//                    for (int i = 0; i < cap2.size(); i++) {
//                        ArrayList<String> key = cap2.get(i).keywords;
//                        for (int j = 0; j < key.size(); j++) {
//                            if (key.get(j).equalsIgnoreCase(search)) {
//                                break;
//                            } else
//                                flag = false;
//
//                        }
//                        if (flag == false)
//                            cap2.remove(i);
//                    }
                }
            });
            //adapter.notifyDataSetChanged();
        }


    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view0, int position, long id) {

            //view1.setContentDescription("Your choice is "+state[position]);
            //view2.setContentDescription("Your choice is "+m[position]);
        }

        public void onNothingSelected(AdapterView<?> parent){

        }
    }



}







