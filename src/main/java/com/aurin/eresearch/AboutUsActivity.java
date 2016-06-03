package com.aurin.eresearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

//import com.aurin.eresearch.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        // twitter image button passing url as parameter
        ImageButton twitter = (ImageButton) findViewById(R.id.twitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this,WebViewActivity.class);
                String url = "https://twitter.com/aurin_org_au";
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
//--------------------------------------------------------------------------------------------------
        // for debug...
//        twitter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AboutUsActivity.this, WebViewActivity.class);
//                String url = "https://twitter.com/aurin_org_au";
//                intent.putExtra("Turl", url);
//                startActivity(intent);
//            }
//        });
//--------------------------------------------------------------------------------------------------

        // face book image button, passing url as parameter
        ImageButton facebook = (ImageButton) findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this,WebViewActivity.class);
                String url = "https://www.facebook.com/Aurin-183080631739008/";
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

//--------------------------------------------------------------------------------------------------
//        facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AboutUsActivity.this, WebViewActivity.class);
//                String url = "https://www.facebook.com/Aurin-183080631739008/";
//                intent.putExtra("Furl", url);
//                startActivity(intent);
//            }
//        });
//--------------------------------------------------------------------------------------------------

        // linkedin image button
        ImageButton linkedin = (ImageButton) findViewById(R.id.instagram);
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this,WebViewActivity.class);
                String url = "https://www.linkedin.com/groups/6622107/profile";
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
//--------------------------------------------------------------------------------------------------
//        linkedin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AboutUsActivity.this, WebViewActivity.class);
//                String url = "https://www.linkedin.com/groups/6622107/profile";
//                intent.putExtra("Lurl", url);
//                startActivity(intent);
//            }
//        });
//--------------------------------------------------------------------------------------------------
    }
}