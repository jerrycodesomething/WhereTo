package com.example.whereto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.*;

import java.util.concurrent.Executor;

public class FragmentMap extends Fragment implements OnMapReadyCallback {

    //Class variables
    private GoogleMap mMap;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code=99;
    private final LatLng defaultLocation = new LatLng(4.2105, 101.9758);
    private double latitude, longitude;
    private int ProximityRadius = 1000;
    private FloatingActionButton categories_btn;
    private FloatingActionButton checkin_btn;
    public FusedLocationProviderClient fusedLocationProviderClient;
    public SupportMapFragment supportMapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            checkUserLocationPermission();
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync((OnMapReadyCallback) this);


        //Button finder
        categories_btn = (FloatingActionButton) view.findViewById(R.id.categories_button);
        checkin_btn = view.findViewById(R.id.check_in_button);


        //On Click listeners
        checkin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(), CheckInActivity.class);
               startActivity(intent);
            }
        });

        categoriesMenu();
        return  view;
    }

    //Categories menu method to show the overlay card for categories
    private void categoriesMenu(){
        categories_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.overlayBottomDrawerTheme);
                View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.overlay_bottom_drawer, (LinearLayout) v.findViewById(R.id.overlay_bottom_drawer_container));

                latitude = lastLocation.getLatitude();
                longitude = lastLocation.getLongitude();
                String food = "food";
                String friends = "friends";
                String mamak = "mamak";
                String kopitiam = "kopitiam";
                String restaurants = "restaurants";
                String cafes = "cafes";


                Object transferData[] = new Object[2];
                GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();

                bottomSheetView.findViewById(R.id.food_cat_text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMap.clear();
                        String url = getUrl(latitude,longitude, food);
                        transferData[0] = mMap;
                        transferData[1] = url;
                        getNearbyPlaces.execute(transferData);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace){

        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitude + ","+ longitude);
        googleURL.append("&radius=" + ProximityRadius);
        googleURL.append("&keyword=" + nearbyPlace);
        googleURL.append("&sensor=true");
        googleURL.append("&key=AIzaSyBWwIjftIps6G7W0qrnCexhqcERwIEnW68");

        Log.d("FragmentMap", "url=" + googleURL.toString());

        return googleURL.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        updateLocationUI();

        getDeviceLocation();

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode){
            case Request_User_Location_Code:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Permission Denied! Please allow the WhereTo app to access location", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    public boolean checkUserLocationPermission (){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            else{
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return  false;
        }
        else{
            return false;
        }
    }

    private void updateLocationUI(){
        if ( mMap == null) {
            return;
        }
        try{
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            }
            else{
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastLocation = null;
                checkUserLocationPermission();
            }
        }
        catch(SecurityException e){
            Toast.makeText(getActivity(), "Permission Denied! Please allow the WhereTo app to access location", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDeviceLocation(){
        try {
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastLocation = task.getResult();
                            if (lastLocation != null) {
                                mMap.setMyLocationEnabled(true);
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastLocation.getLatitude(),
                                                lastLocation.getLongitude()), 15));
                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "Location not found...please enable location services", Toast.LENGTH_SHORT).show();
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, 8));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        }
        catch (SecurityException e)  {
            Toast.makeText(getActivity(), "Permission Denied! Please allow the WhereTo app to access location", Toast.LENGTH_SHORT).show();
        }
    }
}