package com.walkingny.lag_arc_mac2.walkingny;

/*
This class is the Fragment of the Fragment of the Home Tab Fragment
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Image_Child_Fragment_Child extends Fragment {
    int current_position = 0;
    List<JSONObject> resultArr = new ArrayList<>();
    boolean didLoad = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.image_child_fragment_child,container,false);

//        Button refreshBtn = view.findViewById(R.id.refresh_button);

//        refreshBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        Log.e("Hi","Hi");

        Fragment parent = getParentFragment();
        ViewPager viewPager = parent.getView().findViewById(R.id.container2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {


                  Log.e("Position in child is", position+"");
                  current_position = position;
                  try {
                      Log.e("Debug", "" + resultArr.get(current_position).getString("PHOTOID"));
                  }catch (JSONException e){
                      Log.e("Error","JSON Null");
                  }

            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {}

        });


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

                            for (int i = 0; i < response_json_arr.length(); i++)
                            {
                                resultArr.add(response_json_arr.getJSONObject(i));
                                Log.d("Debug",resultArr.get(i)+"");
                            }

                            ImageView imageView = view.findViewById(R.id.imageView);
                            Picasso.with(getActivity())
                                    .load("http://www.laguardiawagnerarchive.lagcc.cuny.edu/PHOTOS/queens/photos/03.001.0670.jpg")
                                    .resize(imageView.getWidth(),imageView.getHeight()).into(imageView);


                        } catch (Throwable t){
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
        return view;
    }
}
