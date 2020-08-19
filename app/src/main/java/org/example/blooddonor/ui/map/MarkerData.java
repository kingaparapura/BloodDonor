package org.example.blooddonor.ui.map;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

public class MarkerData{
    public LatLng latlng;
    public String title;
    public String snippet;
    public float color;

    public MarkerData(LatLng latLng, String title, String snippet, float color){
        this.latlng = latLng;
        this.title = title;
        this.snippet = snippet;
        this.color = color;
    }

    public MarkerData(LatLng latLng, String title, String snippet){
        this.latlng = latLng;
        this.title = title;
        this.snippet = snippet;
        this.color = BitmapDescriptorFactory.HUE_RED;
    }
}
