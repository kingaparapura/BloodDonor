package org.example.blooddonor.ui.map;


import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MarkerData implements ClusterItem {
    public LatLng latlng;
    public String title;
    public String snippet;
    public float color;

    public MarkerData(LatLng latLng, String title, String snippet, float color) {
        this.latlng = latLng;
        this.title = title;
        this.snippet = snippet;
        this.color = color;
    }

    @Override
    public LatLng getPosition() {
        return latlng;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return null;
    }

    public float getColor(){
        return color;
    }



}
