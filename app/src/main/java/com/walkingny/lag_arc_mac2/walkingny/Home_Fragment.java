package com.walkingny.lag_arc_mac2.walkingny;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class Home_Fragment extends Fragment {
    private static final String TAG = "TAB1";
    public int result = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        MyAsyncTask mAsyncTask = new MyAsyncTask();
//        mAsyncTask.doInBackground();
        sendRequest();
        Fragment childFragment = new Image_Child_Fragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, childFragment).commit();
        Log.e("bb ", result + "");
    }


    public void sendRequest() {  //a method to get number of images to be displayed


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        //Todo replace the url with real coordinate
        String url = "http://www.laguardiawagnerarchive.lagcc.cuny.edu/map_app/?command=nearby&lat=40.7439&long=-73.9347";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray response_json_arr = new JSONArray(response);
                            Log.e("My App", response_json_arr.length() + "");
                            result = response_json_arr.length(); //get the number of images
                            Log.e("Result", result + "");

                        } catch (Throwable t) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("My App", "Failed to get HTTP response! Please check your Internet Connection or GPS signal");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    class MyAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                sendRequest();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {

        }
    }
}
