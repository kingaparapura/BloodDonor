package org.example.blooddonor.ui.map;

import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.example.blooddonor.R;

import java.util.ArrayList;


public class MapFragment extends Fragment {

    private MapViewModel notificationsViewModel;
    private GoogleMap googleMap;
    MapView mMapView;
    private LatLngBounds POLAND = new LatLngBounds(
            new LatLng(49, 14), new LatLng(55,24));


   private ArrayList<LatLng> markersList = new ArrayList<LatLng>(); {
        markersList.add(new LatLng(52.232678,21.059987));
        markersList.add(new LatLng(52.404247,16.883133));
        markersList.add(new LatLng(53.125688,23.162624));
        markersList.add(new LatLng(53.126038,18.011930));
        markersList.add(new LatLng(54.365722,18.629010));
        markersList.add(new LatLng(51.770119,18.103634));
        markersList.add(new LatLng(50.255620,19.006320));
        markersList.add(new LatLng(50.873227,20.605351));
        markersList.add(new LatLng(50.056117,19.956267));
        markersList.add(new LatLng(51.248662,22.556440));
        markersList.add(new LatLng(51.782186,19.461452));
        markersList.add(new LatLng(53.793478,20.489954));
        markersList.add(new LatLng(50.670514,17.939014));
        markersList.add(new LatLng(50.087913,18.220049));
        markersList.add(new LatLng(51.397961,21.137523));
        markersList.add(new LatLng(50.033482,22.015322));
        markersList.add(new LatLng(54.469761,17.032590));
        markersList.add(new LatLng(53.436678,14.536697));
        markersList.add(new LatLng(50.774142,16.274951));
        markersList.add(new LatLng(51.115978,17.065825));
        markersList.add(new LatLng(51.940513,15.518990));
}

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);


        mMapView = (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.getUiSettings().setZoomGesturesEnabled(true);
                googleMap.getUiSettings().isScrollGesturesEnabled();
                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(POLAND, 0));

                for (int i=0; i < markersList.size(); i++) {
                    googleMap.addMarker(new MarkerOptions().position(markersList.get(i)));
                }
            }
        });
        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
