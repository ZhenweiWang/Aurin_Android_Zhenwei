package com.android.aurin.aurin_android_zhenwei;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    public  ArrayList<String> attributes = new ArrayList<>();
    public  ArrayList<Double> classfiers = new ArrayList<>();

    private BarChart barchat;


    public static final int SHOW_RESPONSE = 0;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    try {
                        parsingJsonObj(response);
                        for(int i=0; i < attributes.size();i++ ){
                            System.out.println(attributes.get(i));
                            System.out.println(classfiers.get(i));
                        }
                        visual_it(barchat);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        sendRequestWithURLConnection();

        System.out.println("ssssssssssssssssssssssssssssssssssssshow!!!!");
        barchat = (BarChart) findViewById(R.id.chart1);


    }

    private void visual_it(BarChart chart){
        XAxis xAxis = barchat.getXAxis();

        //将X轴的文字描述设置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        ArrayList<String> xValues = new ArrayList<>();

        for(int i=0; i <attributes.size();i++){
            xValues.add(attributes.get(i));
        }


        ArrayList<BarEntry> yValues = new ArrayList<>();

        for(int i=0; i<classfiers.size();i++){
            float yvalue =classfiers.get(i).floatValue();
            yValues.add(new BarEntry(yvalue,i));
        }

        BarDataSet barDataSet = new BarDataSet(yValues,Map_Setting.classifier);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        BarData data = new BarData(xValues,dataSets);
        barchat.setData(data);
        String descip = Map_Setting.attribute.concat("  VS  ").concat(Map_Setting.classifier);
        barchat.setDescription(descip);
        barchat.setBackgroundColor(Color.WHITE);
        barchat.setDrawValueAboveBar(true);
        barchat.setDrawHighlightArrow(true);
        barchat.setDrawBarShadow(true);
//        float alpha = (float) Map_Setting.opacity/100;
//        barchat.setAlpha(alpha);
        barchat.animateXY(3000, 3000);
        barchat.invalidate();

    }




    private void sendRequestWithURLConnection() {
        final String typename = Picked_City.cap_picked.name;
        final double lla = Picked_City.picked_city.getLowerLa();
        final double llo = Picked_City.picked_city.getLowerLon();
        final double hla = Picked_City.picked_city.getHigherLa();
        final double hlo = Picked_City.picked_city.getHigherLon();

        //System.out.println("URL connection");
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                try{
                    URL url = new URL("https://geoserver.aurin.org.au/wfs?" +
                            "request=GetFeature&service=WFS&version=1.1.0&" +
                            "TypeName="+ typename+ "&" +
                            "MaxFeatures=100&outputFormat=json&CQL_FILTER=BBOX" +
                            "(the_geom,"+lla+","+llo+","+hla+","+hlo+")&PropertyName="
                            + Map_Setting.attribute +","+ Map_Setting.classifier);

                    System.out.println(url);
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
                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj = data;
                    handler.sendMessage(message);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parsingJsonObj(String jsonString) throws JSONException {
        JSONObject obj = new JSONObject(jsonString);
        JSONArray jsonArray = new JSONArray(obj.getString("features"));
        for (int i = 0; i < jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String properties = jsonObject.getString("properties");
            JSONObject object = new JSONObject(properties);

            String attribute = object.getString(Map_Setting.attribute);
            attributes.add(attribute);

            String classifier = object.getString(Map_Setting.classifier);
            double classifierd = Double.parseDouble(classifier);
            classfiers.add(classifierd);
        }

    }

}
