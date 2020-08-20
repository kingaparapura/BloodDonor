package org.example.blooddonor.ui.map;

import android.content.Context;
import android.icu.text.BidiRun;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.collections.MarkerManager;

import org.example.blooddonor.R;

import java.util.ArrayList;



public class MapFragment extends Fragment  {

    private MapViewModel notificationsViewModel;
    private GoogleMap googleMap;
    MapView mMapView;
    private LatLngBounds POLAND = new LatLngBounds(
            new LatLng(49, 14), new LatLng(55,24));
    private ClusterManager<MarkerData> mClusterManager;



   private ArrayList<MarkerData> markersList = new ArrayList<MarkerData>(); {
        markersList.add(new MarkerData(new LatLng(52.232731,21.059892),
                "RCKiK Warszawa ul. Saska 63/75", "pon. - pt.: 7.00 - 17.00;\nsb.: 7.00 - 14.00",
                BitmapDescriptorFactory.HUE_RED));
        markersList.add(new MarkerData(new LatLng(52.404270,16.883075),
                "RCKiK Poznań ul. Marcelińska 44", "pon., wt., pt.: 7.00 - 15.00;"  +
                "\nśr, czw.: 7:00 - 18:00;\nsb.: 7.00 - 13.00", BitmapDescriptorFactory.HUE_RED));
        markersList.add(new MarkerData(new LatLng(52.287080, 20.950844),
                "Szpital Bielański ul. Cegłowska 80", "pon.,śr. - pt.: 7.00 - 13.00;" +
                "\nwt.: 11.00 - 16.00;\nsb.: 7.00 - 12.00(wg harmonogramu)", BitmapDescriptorFactory.HUE_YELLOW));
        markersList.add(new MarkerData(new LatLng(52.226204, 21.000898),
                 "Szpital Kliniczny ul. Nowogrodzka 59", "pon. - pt.: 7.00 - 13.00",
                BitmapDescriptorFactory.HUE_YELLOW));
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
                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(POLAND, 0));
                mClusterManager = new ClusterManager<>(getContext(), mMap);
                googleMap.setOnCameraIdleListener(mClusterManager);

                final CustomClusterRenderer renderer = new
                        CustomClusterRenderer(getContext(), mMap, mClusterManager);

                mClusterManager.setRenderer(renderer);

                for (MarkerData data : markersList) {
                    mClusterManager.addItem(data);
                }
                mClusterManager.cluster();
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
