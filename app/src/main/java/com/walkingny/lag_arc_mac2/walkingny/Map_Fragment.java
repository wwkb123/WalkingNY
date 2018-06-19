package com.walkingny.lag_arc_mac2.walkingny;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class Map_Fragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, FragmentLifecycle {
    private static final String TAG = "TAB2";
    private GoogleMap mMap;
    double longitude = 0.0;
    double latitude = 0.0;
    boolean didInitialize = false;
    boolean didStartUpdate = false;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    LocationRequest mLocationRequest;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.map_fragment,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        return view;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        try {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);


        }catch (SecurityException e){

        }

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    private void startLocationUpdates() {
        if(didInitialize){
            try{
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,null /* Looper */);
            }catch (SecurityException e){
                Log.e("Security","Permission not granted!");
            }
        }


    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onPauseFragment() {
        stopLocationUpdates();
        didInitialize = false;
        didStartUpdate = false;
        Log.i(TAG, "onPauseFragment()"+didInitialize);
    }

    @Override
    public void onResumeFragment() {
        Log.i(TAG, "onResumeFragment()"+didInitialize);
        if (!didStartUpdate){
            doUpdates();
        }
        didStartUpdate = true;
    }

    private void stopLocationUpdates() {
        if(didInitialize){
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }


    //---------a method to start updating location---------//
    private void doUpdates(){
        try{
            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    // Got last known location. In some rare situations this can be null.
                                        Log.e("Location from map",location+"");
                                        Log.e("Long from map",location.getLongitude()+"");
                                        Log.e("Lat from map",location.getLatitude()+"");
                                        longitude = location.getLongitude();
                                        latitude = location.getLatitude();
                                }catch(SecurityException e){
                                    Log.e("Security","Permission not granted!");
                                }
                            }
                        }, 100); // delay for 0.1s to load the UI first

                    }else{
                        Log.e("Location from map","null");
                    }
                }
            });
        }catch (SecurityException e){
            Log.e("Error","Permission not granted");
        }


        createLocationRequest(); //create a request


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update location data
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    Log.e("curr Location from map",location+"");
                    Log.e("curr Long from map",longitude+"");
                    Log.e("curr Lat from map",latitude+"");
                    LatLng curr = new LatLng(latitude,longitude);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(curr));
                }
            }
        };
        didInitialize = true;
        startLocationUpdates();  //start updating
    }
    //---------end of method---------//

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
//        mAsyncTask.cancel(true);
        Log.e("stop","stop");
    }
}
