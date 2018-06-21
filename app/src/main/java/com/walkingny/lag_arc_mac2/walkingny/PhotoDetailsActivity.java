package com.walkingny.lag_arc_mac2.walkingny;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class PhotoDetailsActivity extends Activity {

    JSONData photoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);

        Button shareButton = findViewById(R.id.share);
        Button askButton = findViewById(R.id.ask);
        Button backButton = findViewById(R.id.back);

        shareButton.setTransformationMethod(null); //disable all caps text
        askButton.setTransformationMethod(null);
        backButton.setTransformationMethod(null);


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String title = "Walking NY: "+photoData.getAddress()+" - "+photoData.getPhotoDate();
                String contentURL = "Check out the history of my location!\n\n"+"http://www.laguardiawagnerarchive.lagcc.cuny.edu/SearchFromUrl.aspx?&PageToShow=1&Phrasetype=0&SearchType=2&Photos=1&Docs=0&OH=0&Video=0&Art=0&PhotoID=" + photoData.getPhotoName()+"&UniquePHId=PH_"+photoData.getPhotoID();
                i.putExtra(Intent.EXTRA_SUBJECT,title);
                i.putExtra(Intent.EXTRA_TEXT,contentURL);
                startActivity(Intent.createChooser(i,"Share to..."));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoDetailsActivity.super.onBackPressed();
            }
        });



        photoData = new JSONData();
        Intent i = getIntent();
        String arrayToPass = i.getStringExtra("arrayToPass");
        try{
            photoData.parseData(new JSONObject(arrayToPass));
            Log.e("photo is ",photoData.getColID()+" "+photoData.getPhotoName());
            TextView addressText = findViewById(R.id.addressText);
            TextView descText = findViewById(R.id.descText);
            TextView copyrightText = findViewById(R.id.copyrightText);
            ImageView photo = findViewById(R.id.photo);

            //---------getting photo's URL---------//
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


            Picasso.with(this).load(photoURL).resize(photo.getWidth(),400).into(photo);  //display the photo

            //---------display the text---------//
            addressText.setText(photoData.getAddress());
            descText.setText(photoData.getDesc() + "\n"+photoData.getPhotoDate());
            copyrightText.setText(photoData.getCopyright());

        }catch (JSONException e){
            Log.e("JSON is ","null!");
        }


    }
}
