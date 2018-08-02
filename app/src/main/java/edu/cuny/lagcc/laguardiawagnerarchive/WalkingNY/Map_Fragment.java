package edu.cuny.lagcc.laguardiawagnerarchive.WalkingNY;


import android.content.Intent;
import android.location.Location;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Map_Fragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMarkerClickListener, FragmentLifecycle {
    private static final String TAG = "TAB2";
    private GoogleMap mMap;

    //Time Square
    double longitude = -73.9851;
    double latitude = 40.7589;
    int trials = 0;


    boolean didInitialize = false;
    boolean didStartUpdate = false;
    boolean didLoadMarkers = false;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    LocationRequest mLocationRequest;

    //location update interval
    private static final long INTERVAL = 1000 * 10;  //10s
    private static final long FASTEST_INTERVAL = 1000 * 5;  //5s

    JSONArray response_json_arr;

    private ClusterManager<MarkerItem> mClusterManager;
    SupportMapFragment mapFragment;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.map_fragment,container,false);

        loadJSON();

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
        mMap.clear();


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(40.738002399999999, -73.957547599999998);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Test"));

        try {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
//            mMap.setOnMarkerClickListener(mClusterManager);



            setUpClusterer();

            /**
             *  Reserve 0.8 seconds for the device to load the data
             */

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    if(!didLoadMarkers) {
                        if(response_json_arr!=null){
                            for(int i = 0; i < response_json_arr.length(); i++){
                                double markerLat = 0.0;
                                double markerLong = 0.0;
                                String markerAddress = "";
                                //LatLng markerPosition;
                                try{
                                    JSONObject marker = response_json_arr.getJSONObject(i);
                                    JSONData markerData = new JSONData();
                                    markerData.parseData(marker);
                                    markerLat = markerData.getLatitude();
                                    markerLong = markerData.getLongitude();
                                    markerAddress = markerData.getAddress();
//                                markerPosition = new LatLng(markerLat,markerLong);
//                                mMap.addMarker(new MarkerOptions().position(markerPosition).title(markerAddress));

                                    // Add cluster items (markers) to the cluster manager.
                                    addItems(markerLat,markerLong,markerAddress, i);



                                }catch (JSONException e){
                                    Log.e("JSON object","is null");
                                }
                            }//end of for loop

                        } //end of if json null
                        mClusterManager.cluster();
//                    } //end of if didLoadMarkers
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),15));
                }}, 800);



        }catch (SecurityException e){

        }

    }

    //------------------Google Maps buttons------------------//

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        //Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),15));
        return false;
    }

    @Override
    public boolean onMarkerClick (Marker marker){
        //Log.e("Marker id is ",marker.getId());
        return false;
    }
    //------------------end of buttons------------------//




    //------------------methods of setting up clustering------------------//

    private void setUpClusterer() {

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MarkerItem>(getContext(), mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MarkerItem>() {
            @Override
            public boolean onClusterClick(Cluster<MarkerItem> cluster) {
                return false;
            }
        });

        mClusterManager.setOnClusterInfoWindowClickListener(new ClusterManager.OnClusterInfoWindowClickListener<MarkerItem>() {
            @Override
            public void onClusterInfoWindowClick(Cluster<MarkerItem> cluster) {

            }
        });

        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MarkerItem>() {
            @Override
            public boolean onClusterItemClick(MarkerItem markerItem) {
                //Log.e("Marker is",markerItem.getPosition().toString());
                return false;
            }
        });


        /**
         *  When click the Info Window of a marker, pops up a detail info page
         */
        mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<MarkerItem>() {
            @Override
            public void onClusterItemInfoWindowClick(MarkerItem markerItem) {
               // Log.e("Marker title is ",markerItem.getTitle());
                if(response_json_arr!=null){
                    try {

                        //Log.e("MarkerItem json is",response_json_arr.getJSONObject(markerItem.getId())+"");
                        Intent i = new Intent(getActivity(), PhotoDetailsActivity.class);
                        i.putExtra("arrayToPass",response_json_arr.getJSONObject(markerItem.getId()).toString()); //passing the info of that photo
                        getActivity().startActivity(i);  //start the activity

                    }catch(JSONException e){
                        Log.e("JSON","null");
                    }
                }

            }
        });


    }

    private void addItems(double lat, double lng, String title, int id ) {

        // Add ten cluster items in close proximity
//        for (int i = 0; i < 10; i++) {
//            double offset = i / 60d;
//            lat = lat + offset;
//            lng = lng + offset;
            MarkerItem offsetItem = new MarkerItem(lat, lng, title,  "Click to see more info", id);
            mClusterManager.addItem(offsetItem);
//        }

    }

    //------------------a class implements clustering of the markers------------------//
    public class MarkerItem implements ClusterItem {
        private final LatLng mPosition;
        private String mTitle;
        private String mSnippet;
        private int mId;

        public MarkerItem(double lat, double lng) {
            mPosition = new LatLng(lat, lng);
        }

        public MarkerItem(double lat, double lng, String title, String snippet, int id) {
            mPosition = new LatLng(lat, lng);
            mTitle = title;
            mSnippet = snippet;
            mId = id;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public String getTitle() {
            return mTitle;
        }

        @Override
        public String getSnippet() {
            return mSnippet;
        }

        public int getId(){
            return mId;
        }
    }

    //------------------end of class------------------//

    //------------------end of clustering methods------------------//





    //------------------method of loading all the JSON data------------------//

    public void loadJSON(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://www.laguardiawagnerarchive.lagcc.cuny.edu/map_app/?command=json";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            response_json_arr = new JSONArray(response);
                        }catch (JSONException e) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);



    }

    //------------------end of this method------------------//




    //------------------methods of updating location------------------//
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
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


    private void stopLocationUpdates() {
        if(didInitialize){
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }


    //////////---------a method to start updating location---------//////////
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
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curr,15));
                }
            }
        };
        didInitialize = true;
        startLocationUpdates();  //start updating
    }
    //////////------------------end of this method-----------------//////////


    //------------------end of methods section--------------------//




    //------------------Lifecycles------------------//
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
//        mAsyncTask.cancel(true);
        Log.e("stop","stop");
    }


    @Override
    public void onPauseFragment() {
        stopLocationUpdates();
        didInitialize = false;
        didStartUpdate = false;
        mMap.clear();
        didLoadMarkers = true;  //finished
        Log.e(TAG, "onPauseFragment()"+didInitialize);
    }

    @Override
    public void onResumeFragment() {
        Log.e(TAG, "onResumeFragment()"+didInitialize);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (!didStartUpdate){
            doUpdates();
        }
        didStartUpdate = true;
    }
    //------------------end of lifecycles------------------//


}
