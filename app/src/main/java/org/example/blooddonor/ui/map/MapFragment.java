package org.example.blooddonor.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.text.BidiRun;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.google.maps.android.geometry.Bounds;

import org.example.blooddonor.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapFragment extends Fragment {

    private MapViewModel notificationsViewModel;
    private GoogleMap googleMap;
    MapView mMapView;
    private LatLngBounds POLAND = new LatLngBounds(
            new LatLng(49, 14), new LatLng(55, 24));
    private ClusterManager<MarkerData> mClusterManager;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSION = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private ArrayList<MarkerData> markersList = new ArrayList<MarkerData>();
    {
        markersList.add(new MarkerData(new LatLng(52.232731, 21.059892),
                "RCKiK Warszawa ul. Saska 63/75", "pon. - pt.: 7.00 - 17.00;\nsb.: 7.00 - 14.00",
                BitmapDescriptorFactory.HUE_RED));
        markersList.add(new MarkerData(new LatLng(52.404270, 16.883075),
                "RCKiK Poznań ul. Marcelińska 44", "pon., wt., pt.: 7.00 - 15.00;" +
                "\nśr, czw.: 7:00 - 18:00;\nsb.: 7.00 - 13.00", BitmapDescriptorFactory.HUE_RED));
        markersList.add(new MarkerData(new LatLng(52.287080, 20.950844),
                "Szpital Bielański ul. Cegłowska 80", "pon.,śr. - pt.: 7.00 - 13.00;" +
                "\nwt.: 11.00 - 16.00;\nsb.: 7.00 - 12.00(wg harmonogramu)", BitmapDescriptorFactory.HUE_YELLOW));
        markersList.add(new MarkerData(new LatLng(52.226204, 21.000898),
                "Szpital Kliniczny ul. Nowogrodzka 59", "pon. - pt.: 7.00 - 13.00",
                BitmapDescriptorFactory.HUE_YELLOW));
        markersList.add(new MarkerData(new LatLng(52.205354, 21.190975),
                "Centrum Zdrowia Dziecka", "Zamknięte do odwołania!",
                BitmapDescriptorFactory.HUE_YELLOW));
    }

    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<>();
        for (final String permission : REQUIRED_SDK_PERMISSION) {
            final int result = ContextCompat.checkSelfPermission(getContext(), permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSION.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSION, grantResults);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                          @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getContext(), "Required permission '" + permissions[index]
                        + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
        }
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);


        mMapView = (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        checkPermissions();

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
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().isMyLocationButtonEnabled();
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
                mClusterManager.getMarkerCollection()
                        .setInfoWindowAdapter(new InfoWindowCustom(LayoutInflater.from(getContext())));
                mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());
                mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MarkerData>() {
                    @Override
                    public boolean onClusterClick(Cluster<MarkerData> cluster) {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                cluster.getPosition(), (float) Math.floor(googleMap.getCameraPosition()
                                        .zoom + 5)), 300, null);
                        return true;
                    }
                });
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
