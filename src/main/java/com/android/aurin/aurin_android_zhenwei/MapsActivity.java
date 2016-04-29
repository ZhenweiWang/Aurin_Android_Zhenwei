package com.android.aurin.aurin_android_zhenwei;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonLineStringStyle;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;

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
import java.util.Collections;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,GoogleMap.OnMapClickListener {

    private GoogleMap mMap;

    private UiSettings mUiSettings;

    private CheckBox mMyLocationButtonCheckbox;

    private CheckBox mMyLocationLayerCheckbox;

    private static final int MY_LOCATION_PERMISSION_REQUEST_CODE = 1;

    private static final int LOCATION_LAYER_PERMISSION_REQUEST_CODE = 2;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean mPermissionDenied = false;

    private boolean mLocationPermissionDenied = false;

    private ArrayList<LatLng> marks = new ArrayList<>();
    //    JSONArray jsonArray = new JSONArray();
//    JSONObject jobj = new JSONObject();
    SupportMapFragment mapFragment;
    public static final int SHOW_RESPONSE = 0;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    try {
                        JSONObject object = new JSONObject(response);
                        Selected_JSONObj.object = object;
                        GeoJsonLayer layer = new GeoJsonLayer(mapFragment.getMap(), Selected_JSONObj.object);
                        mapsetting(layer);
                       // layer.addLayerToMap();
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


        mMyLocationButtonCheckbox = (CheckBox) findViewById(R.id.mylocationbutton_toggle);
        mMyLocationLayerCheckbox = (CheckBox) findViewById(R.id.mylocationlayer_toggle);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        sendRequestWithURLConnection();


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

        mUiSettings = mMap.getUiSettings();

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mUiSettings.setZoomControlsEnabled(isChecked(R.id.zoom_buttons_toggle));
        mUiSettings.setCompassEnabled(isChecked(R.id.compass_toggle));
        mUiSettings.setMyLocationButtonEnabled(isChecked(R.id.mylocationbutton_toggle));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(isChecked(R.id.mylocationlayer_toggle));
        mUiSettings.setScrollGesturesEnabled(isChecked(R.id.scroll_toggle));
        mUiSettings.setZoomGesturesEnabled(isChecked(R.id.zoom_gestures_toggle));
        mUiSettings.setTiltGesturesEnabled(isChecked(R.id.tilt_toggle));
        mUiSettings.setRotateGesturesEnabled(isChecked(R.id.rotate_toggle));

        double lat = (Picked_City.picked_city.getLowerLa()+Picked_City.picked_city.getHigherLa())/2.0;
        double longi =(Picked_City.picked_city.getLowerLon()+Picked_City.picked_city.getHigherLon())/2.0;
        double hlo = Picked_City.picked_city.getHigherLon();
        double llo = Picked_City.picked_city.getLowerLon();

        int zoom = (int) Math.log(210/(hlo - llo)) + 2;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, longi), zoom));

        enableMyLocation();

        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener(){
            @Override
            public void onPolygonClick(Polygon polygon) {
                System.out.println("clicked!!!");
            }
        });
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }




    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void setZoomButtonsEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        // Enables/disables the zoom controls (+/- buttons in the bottom-right of the map for LTR
        // locale or bottom-left for RTL locale).
        mUiSettings.setZoomControlsEnabled(((CheckBox) v).isChecked());
    }

    public void setCompassEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        // Enables/disables the compass (icon in the top-left for LTR locale or top-right for RTL
        // locale that indicates the orientation of the map).
        mUiSettings.setCompassEnabled(((CheckBox) v).isChecked());
    }

    public void setMyLocationButtonEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        // Enables/disables the my location button (this DOES NOT enable/disable the my location
        // dot/chevron on the map). The my location button will never appear if the my location
        // layer is not enabled.
        // First verify that the location permission has been granted.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mUiSettings.setMyLocationButtonEnabled(mMyLocationButtonCheckbox.isChecked());
        } else {
            // Uncheck the box and request missing location permission.
            mMyLocationButtonCheckbox.setChecked(false);
            requestLocationPermission(MY_LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void setMyLocationLayerEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        // Enables/disables the my location layer (i.e., the dot/chevron on the map). If enabled, it
        // will also cause the my location button to show (if it is enabled); if disabled, the my
        // location button will never show.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(mMyLocationLayerCheckbox.isChecked());
        } else {
            // Uncheck the box and request missing location permission.
            mMyLocationLayerCheckbox.setChecked(false);
            PermissionUtils.requestPermission(this, LOCATION_LAYER_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, false);
        }
    }

    public void setScrollGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        // Enables/disables scroll gestures (i.e. panning the map).
        mUiSettings.setScrollGesturesEnabled(((CheckBox) v).isChecked());
    }

    public void setZoomGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        // Enables/disables zoom gestures (i.e., double tap, pinch & stretch).
        mUiSettings.setZoomGesturesEnabled(((CheckBox) v).isChecked());
    }

    public void setTiltGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        // Enables/disables tilt gestures.
        mUiSettings.setTiltGesturesEnabled(((CheckBox) v).isChecked());
    }

    public void setRotateGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        // Enables/disables rotate gestures.
        mUiSettings.setRotateGesturesEnabled(((CheckBox) v).isChecked());
    }

    public void requestLocationPermission(int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Display a dialog with rationale.
            PermissionUtils.RationaleDialog
                    .newInstance(requestCode, false).show(
                    getSupportFragmentManager(), "dialog");
        } else {
            // Location permission has not been granted yet, request it.
            PermissionUtils.requestPermission(this, requestCode,
                    Manifest.permission.ACCESS_FINE_LOCATION, false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == MY_LOCATION_PERMISSION_REQUEST_CODE) {
            // Enable the My Location button if the permission has been granted.
            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                mUiSettings.setMyLocationButtonEnabled(true);
                mMyLocationButtonCheckbox.setChecked(true);
                enableMyLocation();
            } else {
                mLocationPermissionDenied = true;
            }

        } else if (requestCode == LOCATION_LAYER_PERMISSION_REQUEST_CODE) {
            // Enable the My Location layer if the permission has been granted.
            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMyLocationLayerCheckbox.setChecked(true);
            } else {
                mLocationPermissionDenied = true;
            }
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mLocationPermissionDenied) {
            PermissionUtils.PermissionDeniedDialog
                    .newInstance(false).show(getSupportFragmentManager(), "dialog");
            mLocationPermissionDenied = false;
        }
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
                            "(the_geom,"+lla+","+llo+","+hla+","+hlo+")");

                   // +")&PropertyName="+Map_Setting.attribute+","+Map_Setting.classifier

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

    private void mapsetting(GeoJsonLayer layer){

        //GeoJsonPolygonStyle style;
        //layer.getDefaultPolygonStyle().toPolygonOptions().clickable(true);
        ArrayList<Double> values = new ArrayList<>();

        for (GeoJsonFeature feature : layer.getFeatures()){
            double value = Double.parseDouble(feature.getProperty(Map_Setting.classifier));
            values.add(value);
            System.out.println(value);
        }

        Collections.sort(values);
        double max = values.get(0);
        System.out.println("max======================"+max);
        double min = values.get(values.size() - 1);
        System.out.println("min======================"+min);
        int step =  (int) (max - min)/Integer.parseInt(Map_Setting.level);

        for (final GeoJsonFeature feature : layer.getFeatures()){
            String type = feature.getGeometry().getType();
            if (type.equals("MultiPolygon")){
                double value = Double.parseDouble(feature.getProperty(Map_Setting.classifier));
                int index = (int) (value - min)/step;
                if (Map_Setting.color_select.equals("Red")){
                    //feature.getPolygonStyle().setStrokeWidth(1);
                    GeoJsonPolygonStyle style = new GeoJsonPolygonStyle();
                    style.setFillColor(Colors_Collection.reds.get(index));
                    style.setStrokeWidth(2);
                    style.toPolygonOptions().clickable(true);
                    feature.setPolygonStyle(style);
                    getPosition(feature);
                    layer.addFeature(feature);
                    layer.addLayerToMap();

                }
                else if(Map_Setting.color_select.equals("Blue")){
                    GeoJsonPolygonStyle style = new GeoJsonPolygonStyle();
                    style.setFillColor(Colors_Collection.blues.get(index));
                    style.setStrokeWidth(1);
                    style.toPolygonOptions().clickable(true);
                    feature.setPolygonStyle(style);
                    getPosition(feature);
                    layer.addFeature(feature);
                    layer.addLayerToMap();

                }
                else if (Map_Setting.color_select.equals("Green")){
                    GeoJsonPolygonStyle style = new GeoJsonPolygonStyle();
                    style.setFillColor(Colors_Collection.greens.get(index));
                    style.setStrokeWidth(1);
                    style.toPolygonOptions().clickable(true);
                    feature.setPolygonStyle(style);
                    getPosition(feature);
                    layer.addFeature(feature);
                    layer.addLayerToMap();
                }
                else if (Map_Setting.color_select.equals("Gray")){
                    GeoJsonPolygonStyle style = new GeoJsonPolygonStyle();
                    style.setFillColor(Colors_Collection.grays.get(index));
                    style.setStrokeWidth(1);
                    style.toPolygonOptions().clickable(true);
                    feature.setPolygonStyle(style);
                    getPosition(feature);
                    layer.addFeature(feature);
                    layer.addLayerToMap();

                }
                else{
                    GeoJsonPolygonStyle style = new GeoJsonPolygonStyle();
                    style.setFillColor(Colors_Collection.purples.get(index));
                    style.setStrokeWidth(1);
                    style.toPolygonOptions().clickable(true);
                    feature.setPolygonStyle(style);
                    getPosition(feature);
                    layer.addFeature(feature);
                    layer.addLayerToMap();
                }
            }
            else if (type.equals("MultiLineString")){
                if(Map_Setting.color_select.equals("Red")){
                    GeoJsonLineStringStyle style = new GeoJsonLineStringStyle();
                    style.setColor(Colors_Collection.reds.get(6));
                    feature.setLineStringStyle(style);
                    layer.addFeature(feature);
                    layer.addLayerToMap();
                }
                else if (Map_Setting.color_select.equals("Blue")){
                    GeoJsonLineStringStyle style = new GeoJsonLineStringStyle();
                    style.setColor(Colors_Collection.blues.get(6));
                    feature.setLineStringStyle(style);
                    layer.addFeature(feature);
                    layer.addLayerToMap();
                }
                else if (Map_Setting.color_select.equals("Green")){
                    GeoJsonLineStringStyle style = new GeoJsonLineStringStyle();
                    style.setColor(Colors_Collection.greens.get(6));
                    feature.setLineStringStyle(style);
                    layer.addFeature(feature);
                    layer.addLayerToMap();
                }
                else if (Map_Setting.color_select.equals("Grey")){
                    GeoJsonLineStringStyle style = new GeoJsonLineStringStyle();
                    style.setColor(Colors_Collection.grays.get(6));
                    feature.setLineStringStyle(style);
                    layer.addFeature(feature);
                    layer.addLayerToMap();
                }
                else{
                    GeoJsonLineStringStyle style = new GeoJsonLineStringStyle();
                    style.setColor(Colors_Collection.purples.get(6));
                    feature.setLineStringStyle(style);
                    layer.addFeature(feature);
                    layer.addLayerToMap();
                }

            }
        }
    }

    private boolean isChecked(int id) {
        return ((CheckBox) findViewById(id)).isChecked();
    }

    @Override
    public void onMapClick(LatLng point) {
        mMap.addMarker(new MarkerOptions().position(point).snippet("clicked!"));
        System.out.println("Click!!!!!!!!!!");

    }

    private void getPosition(GeoJsonFeature feature){
        String str = feature.getProperty("bbox");
        String positions = (String) str.subSequence(1, str.length() - 1);
        String[] bbox = positions.split(",");
        double llo = Double.parseDouble(bbox[0]);
        double lla = Double.parseDouble(bbox[1]);
        double hlo = Double.parseDouble(bbox[2]);
        double hla = Double.parseDouble(bbox[3]);
        double centerlo = (llo+hlo)/2.0;
        double centerla = (lla+hla)/2.0;

        LatLng marker_point = new LatLng(centerla,centerlo);

        String title =Map_Setting.attribute.concat(":").concat(feature.getProperty(Map_Setting.attribute));
        String value = Map_Setting.classifier.concat(":").concat(feature.getProperty(Map_Setting.classifier));

        Marker marker = mMap.addMarker(new MarkerOptions().position(marker_point).title(title).snippet(value));
        marker.setAlpha(1);
    }

//    @Override
//    public void onPolygonClick(Polygon polygon) {
//        System.out.println("click!!!!!");
//    }

//    private void addMarker (GeoJsonFeature feature){
//        LatLng center = feature.getBoundingBox().getCenter();
//        Marker marker = mMap.addMarker(new MarkerOptions().position(center).snippet(feature.getProperty(Map_Setting.attribute)));
//    }
}
