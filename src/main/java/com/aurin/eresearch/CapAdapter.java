package com.aurin.eresearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.aurin.eresearch.R;

import java.util.List;

/**
 * Created by apple on 16/4/10.
 * This class defines a adapter for list view
 */
public class CapAdapter extends ArrayAdapter<Capabilities> {

    private int resourceId;

    public CapAdapter(Context context, int textViewResourceId, List<Capabilities> objects){
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
       // capImage.setImageResource(R.drawable.aurin);
        capText.setText(cap.title);
        return view;
    }
}
