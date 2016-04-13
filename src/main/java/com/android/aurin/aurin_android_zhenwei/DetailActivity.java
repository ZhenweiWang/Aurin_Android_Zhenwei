package com.android.aurin.aurin_android_zhenwei;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        if ("action".equals(intent.getAction())) {

            Capabilities cap = (Capabilities)intent.getSerializableExtra("capobj");

            TextView title = (TextView) findViewById(R.id.title_text);
            title.setText(cap.title);

            TextView org = (TextView) findViewById(R.id.org_text);
            org.setText(cap.title);

            TextView abstracts = (TextView) findViewById(R.id.abstract_text);
            abstracts.setText(cap.abstracts);

            TextView lowla = (TextView) findViewById(R.id.lowerla_text);
            lowla.setText(cap.bbox.getLowerLa().toString());

            TextView hila = (TextView) findViewById(R.id.higherla_text);
            hila.setText(cap.bbox.getHigherLa().toString());

            TextView lowlo = (TextView) findViewById(R.id.lowerlo_text);
            lowlo.setText(cap.bbox.getLowerLon().toString());

            TextView hilo = (TextView) findViewById(R.id.higherlo_text);
            hilo.setText(cap.bbox.getHigherLon().toString());

            Button showmap = (Button) findViewById(R.id.show_map);
            showmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailActivity.this,MapsActivity.class);
                    startActivity(intent);
                }
            });

        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
