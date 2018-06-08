package com.walkingny.lag_arc_mac2.walkingny;

/*
This class is the Fragment of the Home Tab Fragment
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Image_Child_Fragment extends Fragment {
    ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.image_child_fragment,container,false);
        viewPager = (ViewPager)view.findViewById(R.id.container2); //a viewpager in image_child_fragment.xml, to put a child fragment in it
        SwipeAdapter swipeAdapter = new SwipeAdapter(getChildFragmentManager());
        viewPager.setAdapter(swipeAdapter); //an adapter that can allow user to swipe between images in the child fragment
        return view;
    }
}
