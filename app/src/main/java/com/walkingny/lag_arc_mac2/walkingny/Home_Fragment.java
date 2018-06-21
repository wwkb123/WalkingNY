package com.walkingny.lag_arc_mac2.walkingny;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;

//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;

public class Home_Fragment extends Fragment implements FragmentLifecycle {
    private static final String TAG = "TAB1";
    //    MyAsyncTask mAsyncTask;
    public int numberOfImages = 0;
    public String arrayToPass = "";

    private int mInterval = 180000; // 180 seconds = 3 minutes
    private Handler refresher; //to refresh the page every 30 seconds

    double longitude = 0.0;
    double latitude = 0.0;
    boolean didInitialize = false;
    boolean firstTime = true;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    LocationRequest mLocationRequest;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    String url = "";


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("granted","yay from home");
                    initializeLocation();
                    doUpdates();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
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

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        final ImageButton refreshBtn = view.findViewById(R.id.refresh_button);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(); //refresh the images
                Log.e("btn","pressed!");
            }
        });

        //button click effect
        refreshBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        refreshBtn.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.refresh_grey));
                        break;
                    case MotionEvent.ACTION_UP:
                        refreshBtn.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.refresh));
                        break;
                }
                return false;
            }
        });



        refresher = new Handler();
        startRepeatingTask(); //refresh every 3 minutes

        TextView textView1 = view.findViewById(R.id.title);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://www.laguardiawagnerarchive.lagcc.cuny.edu/map_app/?command=nearby&lat=40.7439+&long=-73.9347";
            }
        });

        return view;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        //---------Request external storage permission for Google Play services SDK less than version 8.3---------//
//        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//        }


        //---------Request location permission---------//
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//        }

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if(!hasPermissions(getActivity(), PERMISSIONS)){
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        }else{
            doUpdates();
        }////end of else



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
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        initializeLocation(); //load location
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sendRequest(); //load the images
                            }
                        }, 300); //delay for 0.3s to load location first

                    }else{
                        Log.e("Location","null");
                        sendRequest(); //load the images
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
                    url ="http://www.laguardiawagnerarchive.lagcc.cuny.edu/map_app/?command=nearby&lat="+latitude+"&long="+longitude;
                    Log.e("curr Location",location+"");
                    Log.e("curr Long",longitude+"");
                    Log.e("curr Lat",latitude+"");
                }
            }
        };
        didInitialize = true;
        startLocationUpdates();  //start updating
    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
//        // ...
//        super.onSaveInstanceState(outState);
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
        stopLocationUpdates();
//        mAsyncTask.cancel(true);
        Log.e("stop","stop");
    }


    ////////end view/////////


    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                sendRequest();  //refresh every 30 seconds
            } finally {
                // 100% guarantee that this always happens, even if
                // your initializeLocation method throws an exception
                refresher.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        refresher.removeCallbacks(mStatusChecker);
    }


    void initializeLocation(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               try{
                   mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                       @Override
                       public void onSuccess(Location location) {
                           // Got last known location. In some rare situations this can be null.
                           if (location != null) {
                               // Logic to handle location object
                               Log.e("Location",location+"");
                               Log.e("Long",location.getLongitude()+"");
                               Log.e("Lat",location.getLatitude()+"");
                               longitude = location.getLongitude();
                               latitude = location.getLatitude();
                               url ="http://www.laguardiawagnerarchive.lagcc.cuny.edu/map_app/?command=nearby&lat="+latitude+"&long="+longitude;
                           }else{
                               Log.e("Location","null");
                           }
                       }
                   });
               }catch(SecurityException e){
                   Log.e("Security","Permission not granted!");
               }


            }
        }, 100);  // delay for 0.1s to load the UI first
    }

    //////////////////////////////////////////
    /**
     * A method to send HTTP request and get a JSON Array contains info of the images
     */
    void sendRequest(){
//        mAsyncTask = new MyAsyncTask();
//        mAsyncTask.execute();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());



        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray response_json_arr = new JSONArray(response);
                            numberOfImages = response_json_arr.length(); //get the number of images
                            arrayToPass = response_json_arr.toString();
                        }catch (JSONException e) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error+"");
                Toast toast = Toast.makeText(getContext(), R.string.error, Toast.LENGTH_LONG);
                toast.show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        /**
         *  Delay the UI shows up after 0.5 seconds
         */
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               final Bundle bundle = new Bundle();
                bundle.putString("num_of_images", numberOfImages+"");
                bundle.putString("arrayToPass", arrayToPass);
                Fragment childFragment = new Image_Child_Fragment();
                childFragment.setArguments(bundle);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.child_fragment_container, childFragment).commitAllowingStateLoss();

        }}, 500);

        Log.e("request","sent!");

    }

    @Override
    public void onPauseFragment() {
        stopLocationUpdates();
        stopRepeatingTask();
        didInitialize = false;
        firstTime = false;
        Log.e(TAG, "onPauseFragment()"+didInitialize+" "+firstTime);
    }

    @Override
    public void onResumeFragment() {
        Log.e(TAG, "onResumeFragment()"+didInitialize);
        if (didInitialize || !firstTime){
            doUpdates();
        }
        startLocationUpdates();
        startRepeatingTask();
    }
    //////////////////////////////////////////




//    class MyAsyncTask extends AsyncTask<String, Void, Boolean> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected Boolean doInBackground(String... urls) {
//            URL url;
//            HttpURLConnection urlConnection = null;
//            try {
//                url = new URL("http://www.laguardiawagnerarchive.lagcc.cuny.edu/map_app/?command=nearby&lat=40.7439&long=-73.9347");
//
//                urlConnection = (HttpURLConnection) url.openConnection();
//                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                String result = inputStreamToString(in);
//
//
//                /** create a child fragment and pass the number of images */
//
//                in.close();
//            } catch (IOException e) {
//                /** create a child fragment and pass an empty child fragment */
//                Bundle bundle = new Bundle();
//                bundle.putString("num_of_images", numberOfImages+"");
//                bundle.putString("arrayToPass", arrayToPass);
//                Fragment childFragment = new Image_Child_Fragment();
//                childFragment.setArguments(bundle);
//                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                transaction.replace(R.id.child_fragment_container, childFragment).commit();
//            }
//
//            Log.e("request","finished");
//            return null;
//        }
//
//        protected void onPostExecute(Boolean result) {
//
//        }
//    }
//
//    private String inputStreamToString(InputStream is) {
//        String rLine = "";
//        StringBuilder answer = new StringBuilder();
//
//        InputStreamReader isr = new InputStreamReader(is);
//
//        BufferedReader rd = new BufferedReader(isr);
//
//        try {
//            while ((rLine = rd.readLine()) != null) {
//                answer.append(rLine);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return answer.toString();
//    }
}
