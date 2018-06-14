package com.walkingny.lag_arc_mac2.walkingny;

/*
This class is the Fragment of the Home Tab Fragment
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class Image_Child_Fragment extends Fragment {
    ViewPager viewPager;
    ImageView imageView;
    int numberOfImages = 0;
    String arrayToPass = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.image_child_fragment,container,false);
        viewPager = (ViewPager)view.findViewById(R.id.container2); //a viewpager in image_child_fragment.xml, to put a child fragment in it

        Bundle bundle = getArguments();  //get number of images from parent fragment
        numberOfImages= Integer.parseInt(bundle.getString("num_of_images", numberOfImages+"")); //cast String to int
        Log.e("Image child", numberOfImages+"");

        arrayToPass = bundle.getString("arrayToPass", "");


        final SwipeAdapter swipeAdapter = new SwipeAdapter(getChildFragmentManager(), numberOfImages, arrayToPass);
        viewPager.setAdapter(swipeAdapter); //an adapter that can allow user to swipe between images in the child fragment

        TextView errorMsg = view.findViewById(R.id.errorMsg);
        if(numberOfImages==0){
            errorMsg.setVisibility(View.VISIBLE);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
             //   Log.e("curr child", position+"");
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {}

        });


        //TODO: initializeLocation visible and images every 30 seconds
        return view;
    }

    public static class SwipeAdapter extends FragmentStatePagerAdapter {
        private int num_of_images = 0;
        private String arrayToPass = "";
        public SwipeAdapter(FragmentManager fm, int num_of_images_input, String arrayToPass_input){
            super(fm);
            num_of_images = num_of_images_input;
            arrayToPass = arrayToPass_input;
        }
        @Override
        public Fragment getItem(int i){
            return Image_Child_Fragment_Child.newInstance(i,arrayToPass);//get images of the child fragment
        }

        @Override
        public int getCount(){
            return num_of_images; //set number of image fragments
        }
    }

}
