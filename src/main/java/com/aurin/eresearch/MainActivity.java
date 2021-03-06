package com.aurin.eresearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

//import com.aurin.eresearch.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //public ArrayList<Capabilities> allname = new ArrayList<>();
    //public ArrayList<String> titles = new ArrayList<>();
    // define the contents in the spinners.
    private static final String[] state=
            {"Australian Capital Territory","New South Wales","Northern Territory",
                    "Queensland","South Australia","Tasmania","Victoria","Western Australia"};

    private static final String[] act={"Australian Capital Territory"};

    private static final String[] nsw={"Greater Sydney","Capital Region","Central Coast",
            "Central West","Coffs Harbour","Far West and Orana","Hunter Valley exc Newcastle",
            "Illawarra","Mid North Coast","Murray","Newcastle and Lake Macquarie",
            "New England and North West","Riverina","Southern Highlands and Shoalhaven",
            "Sydney - Baulkham Hills and Hawkesbury","Sydney - Blacktown","Sydney - City and Inner South",
            "Sydney - Eastern Suburbs","Sydney - Inner South West","Sydney - Inner West",
            "Sydney - Northern Beaches","Sydney - North Sydney and Hornsby",
            "Sydney - Outer South West","Sydney - Outer West and Blue Mountains","Sydney - Parramatta",
            "Sydney - Ryde","Sydney - South West","Sydney - Sutherland"};

    private static final String[] nt={"Greater Darwin","Northern Territory - Outback"};

    private static final String[] qld={"Greater Brisbane","Brisbane - East","Brisbane Inner City",
            "Brisbane - North","Brisbane - South","Brisbane - West","Cairns","Darling Downs - Maranoa",
            "Fitzroy","Gold Coast","Ipswich","Logan - Beaudesert","Mackay","Moreton Bay - North",
            "Moreton Bay - South","Queensland - Outback","Sunshine Coast","Toowoomba","Townsville",
            "Wide Bay"};

    private static final String[] sau={"Greater Adelaide","Adelaide - Central and Hills",
            "Adelaide - North","Adelaide - South","Adelaide - West","Barossa - Yorke - Mid North",
            "South Australia - Outback","South Australia - South East"};

    private static final String[] tas={"Greater Hobart","Launceston and North East","South East",
            "West and North West"};

    private static final String[] vic={"Greater Melbourne","Melbourne Inner","Melbourne Inner East",
            "Melbourne Inner South","Melbourne North East","Melbourne North West","Melbourne Outer East",
            "Melbourne South East","Melbourne West","Ballarat","Bendigo","Geelong","Hume","Shepparton",
            "North West", "Mornington Peninsula","Warrnambool and South West","Latrobe Gippsland"};

    private static final String[] wau={"Greater Perth","Bunbury","Mandurah","Perth - Inner",
            "Perth - North East","Perth - North West","Perth - South East","Perth - South West",
            "Western Australia - Outback","Western Australia - Wheat Belt"};

    private TextView view1,view2;
    private ImageButton next1;
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



        ImageButton web = (ImageButton) findViewById(R.id.webview);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                String url = "http://aurin.org.au/";
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });



        next1 = (ImageButton) findViewById(R.id.next1);
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
                Picked_City.picked_city = filter_bbox;
               // city_filter(filter_bbox);
                intent.putExtra("bbox", filter_bbox);
                System.out.println(filter_bbox.getHigherLa());
                startActivity(intent);
            }
        });



        ImageButton aboutus = (ImageButton) findViewById(R.id.about_us);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AboutUsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void city_filter(BBOX city){
        double city_lowla = city.getLowerLa();
        double city_lowlo = city.getLowerLon();
        double city_hila = city.getHigherLa();
        double city_hilo = city.getHigherLon();

        for (Capabilities cap : AllDatastes.lists){
            if (cap.bbox.getHigherLon()<city_lowlo && cap.bbox.getHigherLa()<city_lowla){
                AllDatastes.lists.remove(cap);
            }
            else if(cap.bbox.getLowerLa()>city_hila && cap.bbox.getLowerLon()>city_hilo){
                AllDatastes.lists.remove(cap);
            }

        }

    }

// sending http request for all the datasets.
    private void sendRequestWithURLConnection() {
        //System.out.println("URL connection");
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                try{
                    URL url = new URL("https://geoserver.aurin.org.au/wfs?service=WFS&" +
                            "version=1.1.0&request=GetCapabilities");
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

    // parsing the XML with pull method
    private void parseXMLWithPull (String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();

            String name ="";
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
                            name = xmlPullParser.nextText();
                            String[] array = name.split(":");
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
                            cap.name = name;
                            cap.title = title;
                            cap.organization = organization;
                            cap.abstracts = abstracts;
                            cap.keywords = keywords;
                            cap.bbox = bbox;
                            switch (organization){
                                case "ACARA":
                                    cap.image_id=R.drawable.acara;
                                    break;
                                case "Landgate":
                                    cap.image_id=R.drawable.landgate;
                                    break;
                                case "DSDBI":
                                    cap.image_id=R.drawable.dsdbi;
                                    break;
                                case "RIA":
                                    cap.image_id=R.drawable.ria;
                                    break;
                                case "Stocks_and_Flows":
                                    cap.image_id=R.drawable.stocks_and_flows;
                                    break;
                                case "grattan":
                                    cap.image_id=R.drawable.grattan;
                                    break;
                                case "gu_urp":
                                    cap.image_id=R.drawable.gu_urp;
                                    break;
                                case "melb_water":
                                    cap.image_id=R.drawable.melb_water;
                                    break;
                                case "nm":
                                    cap.image_id=R.drawable.nm;
                                    break;
                                case "qld_govt_qtt":
                                    cap.image_id=R.drawable.qld_govt_qtt;
                                    break;
                                case "ua_apmrc":
                                    cap.image_id=R.drawable.ua_apmrc;
                                    break;
                                case "vic_govt_delwp":
                                    cap.image_id=R.drawable.vic_govt_delwp;
                                    break;
                                case "vic_lgovt_hume":
                                    cap.image_id=R.drawable.vic_lgovt_hume;
                                    break;
                                case "vichealth":
                                    cap.image_id=R.drawable.vichealth;
                                    break;
                                default:
                                    cap.image_id=R.drawable.aurin;

                            }
                            //cap.image_id = R.drawable.organization;

                            //titles.add(cap.title);
                            if(! Big_Data.big_data.contains(cap.title))
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

    // obtian areas related to the state.
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
