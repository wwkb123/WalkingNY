package com.walkingny.lag_arc_mac2.walkingny;

/*
This class is the Fragment of the Home Tab Fragment
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

import org.json.JSONArray;


public class Image_Child_Fragment extends Fragment {
    ViewPager viewPager;
    int numberOfImages = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.image_child_fragment,container,false);
        viewPager = (ViewPager)view.findViewById(R.id.container2); //a viewpager in image_child_fragment.xml, to put a child fragment in it

        Bundle bundle = getArguments();  //get number of images from parent fragment
        numberOfImages= Integer.parseInt(bundle.getString("num_of_images", numberOfImages+"")); //cast String to int
        Log.e("Image child", numberOfImages+"");

        SwipeAdapter swipeAdapter = new SwipeAdapter(getChildFragmentManager(), numberOfImages);
        viewPager.setAdapter(swipeAdapter); //an adapter that can allow user to swipe between images in the child fragment

        TextView errorMsg = view.findViewById(R.id.errorMsg);
        if(numberOfImages==0){
            errorMsg.setVisibility(View.VISIBLE);
        }


        //TODO: update visible and images every 30 seconds
        return view;
    }


}
