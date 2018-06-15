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
    JSONData photoData; //a custom object that shares the same format of the JSON data retrieved from the HTTP response

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

        photoData = new JSONData();
        if(arrayToPass!=null){
            try{
                //tvLabel.setText(arrayToPass.getJSONObject(current_position).getString("PHOTOID"));       //preserved for testing

                ImageView imageView = view.findViewById(R.id.imageView);
                Log.e("JSON",""+arrayToPass.getJSONObject(current_position));

                photoData.parseData(arrayToPass.getJSONObject(current_position));
                String photoURL = "http://www.laguardiawagnerarchive.lagcc.cuny.edu/PHOTOS/";

                switch (photoData.getColID()){
                    case "01":
                        photoURL = photoURL+"laguardia/photos/" + photoData.getPhotoName()+".jpg";
                        break;
                    case "02":
                        photoURL = photoURL+"nycha/photos/" + photoData.getPhotoName()+".jpg";
                        break;
                    case "03":
                        photoURL = photoURL+"queens/photos/" + photoData.getPhotoName()+".jpg";
                        break;
                    case "04":
                        photoURL = photoURL+"steinway/photos/" + photoData.getPhotoName()+".jpg";
                        break;
                    case "05":
                        photoURL = photoURL+"NYCC/" + photoData.getPhotoName()+".jpg";
                        break;
                    case "06":
                        photoURL = photoURL+"wagner/photos/" + photoData.getPhotoName()+".jpg";
                        break;
                    case "07":
                        photoURL = photoURL+"beame/photos/" + photoData.getPhotoName()+".jpg";
                        break;
                    case "08":
                        photoURL = photoURL+"koch/photos/" + photoData.getPhotoName()+".jpg";
                        break;
                    case "09":
                        photoURL = photoURL+"GIULIANI/photos/" + photoData.getPhotoName()+".jpg";
                        break;
                    case "10":
                        photoURL = photoURL+"Lindsay/photos/" + photoData.getPhotoName()+".jpg";
                        break;
                    case "11":
                        photoURL = photoURL+"Dinkins/photos/" + photoData.getPhotoName()+".jpg";
                        break;
                    case "12":
                        photoURL = photoURL+"Impellitteri/photos/" + photoData.getPhotoName()+".jpg";
                        break;
                    case "13":
                        photoURL = photoURL+"ODwyer/photos/" + photoData.getPhotoName()+".jpg";
                        break;
                    default:
                        photoURL = photoURL+"MAP_APP_PHOTOS/" + photoData.getPhotoName()+".jpg";
                        break;
                }


                Picasso.with(getActivity()).load(photoURL).resize(300,300).into(imageView);  //display the photo


            }catch (JSONException e){
                tvLabel.setText("null");
                Log.e("JSON id","null!");
            }
        }


//        ImageView imageView = view.findViewById(R.id.imageView);
//        Picasso.with(getActivity())
//                .load("http://www.laguardiawagnerarchive.lagcc.cuny.edu/PHOTOS/queens/photos/03.001.0670.jpg")
//                .resize(300,300).into(imageView);
//        switch (current_position){
//            case 0:
//                Log.e("image's json id",current_position+"");
//
//                break;
//            case 1:
//                Log.e("image's json id",current_position+"");
//                Picasso.with(getActivity())
//                        .load("https://i.imgur.com/XgxWfyF.png")
//                        .resize(300,300).into(imageView);
//                break;
//            case 2:
//                Log.e("image's json id",current_position+"");
//                Picasso.with(getActivity())
//                        .load("http://3.bp.blogspot.com/-zPzIKEftmYI/VRO88LNY72I/AAAAAAAAMp8/ZfugI8aOdf4/s1600/Scroll%2BViewPager.png")
//                        .resize(300,300).into(imageView);
//                break;
//            default:
//                break;
//        }



//        Button refreshBtn = view.findViewById(R.id.refresh_button);

//        refreshBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


/*
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());

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
