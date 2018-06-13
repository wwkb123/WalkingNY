package com.walkingny.lag_arc_mac2.walkingny;

/*
This class is the Fragment of the Fragment of the Home Tab Fragment
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class Image_Child_Fragment_Child extends Fragment {
    int current_position = 0;
    JSONArray arrayToPass;

    public static Image_Child_Fragment_Child newInstance(int position, String array) {
        Image_Child_Fragment_Child fragment = new Image_Child_Fragment_Child();
        Bundle args = new Bundle();
        args.putInt("current_position", position);
        args.putString("arrayToPass",array);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        current_position = getArguments().getInt("current_position", 0);
        try{
            arrayToPass = new JSONArray(getArguments().getString("arrayToPass",""));
        }catch (JSONException e){
            arrayToPass = null;
        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.image_child_fragment_child,container,false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tv);
        tvLabel.setText(current_position+"");
        if(arrayToPass!=null){
            try{
                Log.e("PHOTO ID",current_position+" "+arrayToPass.getJSONObject(current_position).getString("PHOTOID"));
            }catch (JSONException e){
                Log.e("JSON id","null!");
            }
        }
        Log.e("JSON id",current_position+"");
        ImageView imageView = view.findViewById(R.id.imageView);
        switch (current_position){
            case 0:
                Log.e("image's json id",current_position+"");
                Picasso.with(getActivity())
                        .load("http://www.laguardiawagnerarchive.lagcc.cuny.edu/PHOTOS/queens/photos/03.001.0670.jpg")
                        .resize(300,300).into(imageView);
                break;
            case 1:
                Log.e("image's json id",current_position+"");
                Picasso.with(getActivity())
                        .load("https://i.imgur.com/XgxWfyF.png")
                        .resize(300,300).into(imageView);
                break;
            case 2:
                Log.e("image's json id",current_position+"");
                Picasso.with(getActivity())
                        .load("http://3.bp.blogspot.com/-zPzIKEftmYI/VRO88LNY72I/AAAAAAAAMp8/ZfugI8aOdf4/s1600/Scroll%2BViewPager.png")
                        .resize(300,300).into(imageView);
                break;
            default:
                break;
        }



//        Button refreshBtn = view.findViewById(R.id.refresh_button);

//        refreshBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        Bundle bundle = getArguments();
//        current_position = bundle.getInt("currPosition",0);
//        Log.e("new child position",current_position+"");
//        try {
//            arrayToPass = new JSONArray(bundle.getString("arrayToPass", ""));
//        }catch (JSONException e){
//            arrayToPass = null;
//        }
//        Fragment parent = getParentFragment();
//        ViewPager viewPager = parent.getView().findViewById(R.id.container2);
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//
//
//                  Log.e("Position in child is", position+"");
//                  current_position = position;
//                  try {
//                      if(arrayToPass!=null){
//
//                          Log.e("Debug", "" + arrayToPass.getJSONObject(current_position).getString("PHOTOID"));
//                      }
//
//                  }catch (JSONException e){
//                      Log.e("Error","JSON Null");
//                  }
//
//            }
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {}
//
//        });

/*
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

        */

        return view;
    }
}
