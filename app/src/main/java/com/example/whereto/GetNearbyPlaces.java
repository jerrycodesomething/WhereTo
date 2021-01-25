package com.example.whereto;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlaces extends AsyncTask<Object, String, String> {

    private String googlePlaceData, url;
    private GoogleMap mMap;
    private UserLocation mUserLocation;
    private FusedLocationProviderClient mFusedLocation;
    StorageReference storageReference;
    FirebaseUser user;
    FirebaseAuth fAuth;
    FirebaseFirestore mDB;
    String userId;

    public static final String TAG = "TAG";

    private void DisplayNearbyPlaces(List<HashMap<String, String>> NearbyPlacesList){
        for(int i=0; i<NearbyPlacesList.size(); i++){
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
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
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

//    private void getUserDetails(){
//        if(mUserLocation != null){
//            mUserLocation = new UserLocation();
//            DocumentReference userRef = mDB.collection("users").document(userId);
//
//            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if(task.isSuccessful()){
//                        Log.d(TAG, "onComplete: successfully get the user details. ");
//
//                        User user = task.getResult().toObject(User.class);
//                        mUserLocation.setUser(user);
//                        DisplayNearbyPlaces();
//                    }
//                }
//            });
//        }
//    }

//    private void saveUserLocation(){
//
//        fAuth = FirebaseAuth.getInstance();
//        mDB = FirebaseFirestore.getInstance();
//        userId = fAuth.getCurrentUser().getUid();
//        if(mUserLocation != null){
//            DocumentReference locationRef = mDB.collection("users").document(userId);
//
//            locationRef.set(mUserLocation).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        Log.d(TAG,"saveUserLocation: \ninserted user location into database" +
//                                "\n latitude: " + mUserLocation.getGeo_point().getLatitude() +
//                                "\n longitude: " + mUserLocation.getGeo_point().getLongitude());
//
////                        mUserLocation.setGeo_point(geoPoint);
////                        mUserLocation.setTimestamp(null);
////                        saveUserLocation();
//                    }
//                }
//            });
//        }
//    }




}
