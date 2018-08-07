package edu.cuny.lagcc.laguardiawagnerarchive.WalkingNY;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.squareup.picasso.Picasso;

import me.relex.photodraweeview.PhotoDraweeView;

public class FullScreenPhotoActivity extends AppCompatActivity {
//    private ImageView photo;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_screen_photo);

        Intent i = getIntent();
        String photoURL = i.getStringExtra("photoURL");
//        photo = findViewById(R.id.fullPhoto);

        final PhotoDraweeView mPhotoDraweeView = findViewById(R.id.fullPhoto);
        mPhotoDraweeView.setPhotoUri(Uri.parse(photoURL));



//        Picasso.with(this).load(photoURL).resize(photo.getWidth(),300).into(photo);  //display the photo
//        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent motionEvent) {
//        mScaleGestureDetector.onTouchEvent(motionEvent);
//        return true;
//    }
//
//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
//            mScaleFactor *= scaleGestureDetector.getScaleFactor();
//            mScaleFactor = Math.max(0.1f,
//                    Math.min(mScaleFactor, 5.0f));
//            photo.setScaleX(mScaleFactor);
//            photo.setScaleY(mScaleFactor);
//            return true;
//        }
//    }
}
