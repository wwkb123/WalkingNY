package com.walkingny.lag_arc_mac2.walkingny;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Home_Fragment extends Fragment {
    private static final String TAG = "TAB1";
    //    MyAsyncTask mAsyncTask;
    public int numberOfImages = 0;
    public String arrayToPass = "";

    private int mInterval = 30000; // 30 seconds
    private Handler refresher; //to refresh the page every 30 seconds

    double longitude = 0.0;
    double latitude = 0.0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);




        sendRequest(); //load the images

        ImageButton refreshBtn = view.findViewById(R.id.refresh_button);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(); //refresh the images
                Log.e("btn","pressed!");
            }
        });


        refresher = new Handler();
        startRepeatingTask(); //refresh every 30 seconds



        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        longitude = bundle.getDouble("longitude");
        latitude = bundle.getDouble("latitude");

        Log.e("Long in fragment",longitude+"");
        Log.e("Lat in fragment",latitude+"");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
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
                // your update method throws an exception
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


    //////////////////////////////////////////
    /**
     * A method to send HTTP request and get a JSON Array contains info of the images
     */
    void sendRequest(){
//        mAsyncTask = new MyAsyncTask();
//        mAsyncTask.execute();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        //Todo replace the url with real coordinate
        String url ="http://www.laguardiawagnerarchive.lagcc.cuny.edu/map_app/?command=nearby&lat=40.7439&long=-73.9347";

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
                Bundle bundle = new Bundle();
                bundle.putString("num_of_images", numberOfImages+"");
                bundle.putString("arrayToPass", arrayToPass);
                Fragment childFragment = new Image_Child_Fragment();
                childFragment.setArguments(bundle);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.child_fragment_container, childFragment).commit();
            }
        }, 500);

        Log.e("request","sent!");

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
