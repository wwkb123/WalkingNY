package com.walkingny.lag_arc_mac2.walkingny;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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

        photoData = new JSONData();
        Intent i = getIntent();
        String arrayToPass = i.getStringExtra("arrayToPass");
        try{
            photoData.parseData(new JSONObject(arrayToPass));
            Log.e("photo is ",photoData.getColID()+" "+photoData.getPhotoName());

        }catch (JSONException e){
            Log.e("JSON is ","null!");
        }


    }
}
