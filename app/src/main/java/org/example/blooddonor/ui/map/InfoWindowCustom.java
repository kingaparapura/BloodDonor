package org.example.blooddonor.ui.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import org.example.blooddonor.R;

public class InfoWindowCustom implements GoogleMap.InfoWindowAdapter {

    LayoutInflater inflater;

    public InfoWindowCustom (LayoutInflater inflater) {
        this.inflater = inflater;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View view = inflater.inflate(R.layout.info_window, null);

        TextView title = (TextView)
                view.findViewById(R.id.adress);

        TextView subtitle = (TextView)
                view.findViewById(R.id.hours);

        title.setText(marker.getTitle());
        subtitle.setText(marker.getSnippet());
        return view;
    }
}
