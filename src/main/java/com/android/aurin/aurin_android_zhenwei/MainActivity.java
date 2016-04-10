package com.android.aurin.aurin_android_zhenwei;

import android.content.Intent;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Capabilities> allname = new ArrayList<>();
    public ArrayList<String> titles = new ArrayList<>();

    private static final String[] state=
            {"Australian Capital Territory","New South Wales","Northern Territory",
                    "Queensland","South Australia","Tasmania","Victoria","Western Australia"};
    private static final String[] act={"Australian Capital Territory"};
    private static final String[] nsw={"Greater Sydney","Rest of NSW"};
    private static final String[] nt={"Greater Darwin","Rest of NT"};
    private static final String[] qld={"Greater Brisbane","Rest of Qld"};
    private static final String[] sau={"Greater Adelaide","Rest of SA"};
    private static final String[] tas={"Greater Hobart","Rest of Tas"};
    private static final String[] vic={"Greater Melbourne","Rest of Vic"};
    private static final String[] wau={"Greater Perth","Rest of WA"};

    private TextView view1,view2;
    private Button next1;
    private Spinner spinner1,spinner2;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendRequestWithURLConnection();

        view1 = (TextView) findViewById(R.id.spinner1_textview);
        view2 = (TextView) findViewById(R.id.spinner2_textview);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,state);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(new SpinnerSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view0, int position, long id) {

                String item = state[position];
                getArea(item);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner1.setVisibility(View.VISIBLE);



        Button web = (Button) findViewById(R.id.webview);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                startActivity(intent);
            }
        });



        next1 = (Button) findViewById(R.id.next1);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              sendRequestWithURLConnection();
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                intent.setAction("action");
                //Bundle bundle = new Bundle();
                //bundle.putParcelableArrayList("titles", titles);
                String bbox = spinner2.getSelectedItem().toString();
                BBOX filter_bbox = City_BBOX.city_bbox.get(bbox);
                intent.putExtra("bbox", filter_bbox);
                System.out.println(filter_bbox.getHigherLa());
                startActivity(intent);
            }
        });



        Button locate = (Button) findViewById(R.id.location);
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void sendRequestWithURLConnection() {
        //System.out.println("URL connection");
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                try{
                    URL url = new URL("https://geoserver.aurin.org.au/wfs?service=WFS&version=1.1.0&request=GetCapabilities");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //System.out.println(" connection complete ");
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    //System.out.println("line complete");
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    String data = response.toString();
                    //System.out.println(data);
                    parseXMLWithPull(data);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseXMLWithPull (String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();

            String title = "";
            String abstracts = "";
            String organization = "";
            BBOX bbox = new BBOX();

            ArrayList<String> keywords = new ArrayList<>();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if ("Name".equals(nodeName)) {
                            String[] array = xmlPullParser.nextText().split(":");
                            organization = array[0];
                        }
                        else if ("Title".equals(nodeName)){
                            title = xmlPullParser.nextText();
                        }
                        else if ("Abstract".equals(nodeName)){
                            abstracts = xmlPullParser.nextText();
                        }
                        else if ("ows:Keyword".equals(nodeName)){
                            String keyword = xmlPullParser.nextText();
                            keywords.add(keyword);
                        }

                        else if ("ows:LowerCorner".equals(nodeName)){
                            String[] lowerCorner = xmlPullParser.nextText().split(" ");
                            bbox.setLowerLon(Double.parseDouble(lowerCorner[0]));
                            bbox.setLowerLa(Double.parseDouble(lowerCorner[1]));
                        }
                        else if ("ows:UpperCorner".equals(nodeName)){
                            String[] upperCorner = xmlPullParser.nextText().split(" ");
                            bbox.setHigherLon(Double.parseDouble(upperCorner[0]));
                            bbox.setHigherLa(Double.parseDouble(upperCorner[1]));
                        }

                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        if ("FeatureType".equals(nodeName)) {
                            Capabilities cap = new Capabilities();
                            cap.title = title;
                            cap.organization = organization;
                            cap.abstracts = abstracts;
                            cap.keywords = keywords;
                            cap.bbox = bbox;

                            titles.add(cap.title);
                            AllDatastes.lists.add(cap);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getArea(String str){
        String item=str;
        System.out.println(item);
        if(item.equals("Australian Capital Territory")){
            adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,act);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(new SpinnerSelectedListener());
            spinner2.setVisibility(View.VISIBLE);
        }
        else if(item.equals("New South Wales")){
            adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,nsw);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(new SpinnerSelectedListener());
            spinner2.setVisibility(View.VISIBLE);
        }
        else if(item.equals("Northern Territory")){
            adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,nt);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(new SpinnerSelectedListener());
            spinner2.setVisibility(View.VISIBLE);
        }
        else if(item.equals("Queensland")){
            adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,qld);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(new SpinnerSelectedListener());
            spinner2.setVisibility(View.VISIBLE);
        }
        else if(item.equals("South Australia")){
            adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sau);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(new SpinnerSelectedListener());
            spinner2.setVisibility(View.VISIBLE);
        }
        else if(item.equals("Tasmania")){
            adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tas);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(new SpinnerSelectedListener());
            spinner2.setVisibility(View.VISIBLE);
        }
        else if(item.equals("Victoria")){
            adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,vic);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(new SpinnerSelectedListener());
            spinner2.setVisibility(View.VISIBLE);
        }
        else{
            adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,wau);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(new SpinnerSelectedListener());
            spinner2.setVisibility(View.VISIBLE);
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
