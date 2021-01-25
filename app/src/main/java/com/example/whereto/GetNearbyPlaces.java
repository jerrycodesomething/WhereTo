package com.example.whereto;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlaces extends AsyncTask<Object, String, String> {

    private String googlePlaceData, url;
    private GoogleMap mMap;

    private void DisplayNearbyPlaces(List<HashMap<String, String>> NearbyPlacesList) {
        for (int i = 0; i < NearbyPlacesList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String, String> googleNeabyPlace = NearbyPlacesList.get(i);
            String nameOfPlace = googleNeabyPlace.get("place_name");
            String vicinity = googleNeabyPlace.get("vicinity");
            double lat = Double.parseDouble(googleNeabyPlace.get("lat"));
            double lng = Double.parseDouble(googleNeabyPlace.get("lng"));

            LatLng latLng = new LatLng(lat, lng);

            markerOptions.position(latLng);
            markerOptions.title(nameOfPlace + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            mMap.addMarker(markerOptions);
        }
    }

    @Override
    protected String doInBackground(Object... objects){
        mMap= (GoogleMap) objects[0];
        url= (String) objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try{
            googlePlaceData= downloadUrl.ReadTheURL(url);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return googlePlaceData;
    }

    @Override
    protected void onPostExecute(String s){
        List<HashMap<String, String>> NearbyPlacesList = null;
        DataParse dataParse = new DataParse();
        NearbyPlacesList= dataParse.parse(s);
        DisplayNearbyPlaces(NearbyPlacesList);
    }




}
