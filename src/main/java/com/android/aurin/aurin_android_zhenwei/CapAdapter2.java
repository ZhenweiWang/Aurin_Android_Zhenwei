package com.android.aurin.aurin_android_zhenwei;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by apple on 16/5/8.
 */
public class CapAdapter2
        extends ArrayAdapter<Capabilities> {

    private int resourceId;

    public CapAdapter2(Context context, int textViewResourceId, List<Capabilities> objects){
        super (context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Capabilities cap = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView capImage = (ImageView) view.findViewById(R.id.cap_image);
        TextView capText = (TextView) view.findViewById(R.id.cap_name);
        capImage.setImageResource(cap.image_id);
        capText.setText(cap.title);
        return view;
    }
}