package org.example.blooddonor.ui.map;

import com.google.android.gms.maps.model.LatLng;

public class MarkerData{
    public LatLng latlng;
    public String title;
    public String snippet;

    public MarkerData(LatLng latLng, String title, String snippet){
        this.latlng = latLng;
        this.title = title;
        this.snippet = snippet;


    }
}
