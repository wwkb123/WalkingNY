package com.walkingny.lag_arc_mac2.walkingny;

/*
This class is the Fragment of the Home Tab Fragment
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;



public class Image_Child_Fragment extends Fragment {
    private static final String TAG = "TAG";

    ViewPager viewPager;
    ImageView imageView;
    int numberOfImages = 0;
    String arrayToPass = "";
    int current_position = 0;
    JSONArray jsonArray;
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.image_child_fragment,container,false);
        viewPager = (ViewPager)view.findViewById(R.id.container2); //a viewpager in image_child_fragment.xml, to put a child fragment in it

        Bundle bundle = getArguments();  //get number of images from parent fragment
        numberOfImages= Integer.parseInt(bundle.getString("num_of_images", numberOfImages+"")); //cast String to int
        Log.e("Image child", numberOfImages+"");

        arrayToPass = bundle.getString("arrayToPass", "");


        try{
            jsonArray = new JSONArray(arrayToPass);
        }catch (JSONException e){
            jsonArray = null;
            Log.e("JSON","null");
        }

//        final GifTextView loadingImg = view.findViewById(R.id.loading); //GIF image of loading
//        TextView errorMsg = view.findViewById(R.id.errorMsg);

        final SwipeAdapter swipeAdapter = new SwipeAdapter(getChildFragmentManager(), numberOfImages, arrayToPass);
        viewPager.setAdapter(swipeAdapter); //an adapter that can allow user to swipe between images in the child fragment


        if(numberOfImages==0){
            //errorMsg.setVisibility(View.VISIBLE);
            Toast toast = Toast.makeText(getContext(), R.string.error, Toast.LENGTH_LONG);
            toast.show();

        }else{
            //loadingImg.setVisibility(View.INVISIBLE);
        }

//        final Handler delayer = new Handler();  //delay displaying images for 0.5s
//        delayer.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//
//            }}, 500);



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
             //   Log.e("curr child", position+"");
//                Fragment child = getChildFragmentManager().getFragments().get(position);
//                TextView currPos = child.getView().findViewById(R.id.hidden);
                current_position = position;
                Log.e("wow","current position from child is "+position);
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {}

        });

        final ImageButton detailsButton = getParentFragment().getView().findViewById(R.id.details_Button);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("button curr pos",current_position+"");

                try{
                    if(jsonArray!=null){
                        Intent i = new Intent(getActivity(), PhotoDetailsActivity.class);
                        //Log.e("button curr array",jsonArray.getJSONObject(current_position)+"");
                        i.putExtra("arrayToPass", jsonArray.getJSONObject(current_position).toString()); //passing the info of that photo
                        getActivity().startActivity(i); //start the activity
                    }

                }catch (JSONException e){
                    Log.e("JSON","null");
                }

            }
        });

        detailsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        detailsButton.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.details_grey));
                        break;
                    case MotionEvent.ACTION_UP:
                        detailsButton.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.details));
                        break;
                }
                return false;
            }
        });
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
