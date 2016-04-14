package com.android.aurin.aurin_android_zhenwei;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
//    JSONArray jsonArray = new JSONArray();
//    JSONObject jobj = new JSONObject();
    SupportMapFragment mapFragment;
    public static final int SHOW_RESPONSE = 0;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg){

            switch (msg.what){
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    try {
                        JSONObject object = new JSONObject(response);
                        Selected_JSONObj.object = object;
                        GeoJsonLayer layer = new GeoJsonLayer(mapFragment.getMap(), Selected_JSONObj.object);
                        layer.addLayerToMap();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        sendRequestWithURLConnection();

//        for(int i=0; i<jsonArray.length();i++){
//            try {
//                System.out.println(jsonArray.get(2).toString());
//                GeoJsonLayer layer = new GeoJsonLayer(mapFragment.getMap(), (JSONObject) jsonArray.get(i));
//                layer.addLayerToMap();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        System.out.println("aaaaaa: " + Selected_JSONObj.object.toString());
//        GeoJsonLayer layer = new GeoJsonLayer(mapFragment.getMap(), Selected_JSONObj.object);
//        layer.addLayerToMap();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void sendRequestWithURLConnection() {
        //System.out.println("URL connection");
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                try{
                    URL url = new URL("https://geoserver.aurin.org.au/wfs?" +
                            "request=GetFeature&service=WFS&version=1.1.0&" +
                            "TypeName=grattan:Grattan_Job_Access2011&" +
                            "MaxFeatures=1000&outputFormat=json&CQL_FILTER=BBOX" +
                            "(the_geom,-37.843287468235644,144.88364340276473,-37.7613640945703,145.05084158388618)");
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
                    System.out.println(data);
//                    JSONArray jsonArray1 = new JSONArray(data);
//                    jsonArray = jsonArray1;
//                    JSONObject object = new JSONObject(data);
//                    System.out.println("bbb"+object.toString());
//                    Selected_JSONObj.object = object;
////                    GeoJsonLayer layer = new GeoJsonLayer(mapFragment.getMap(), Selected_JSONObj.object);
////                    layer.addLayerToMap();
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
}