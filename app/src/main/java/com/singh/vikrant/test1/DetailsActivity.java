package com.singh.vikrant.test1;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.singh.vikrant.test1.database.Anime_Model;
import com.singh.vikrant.test1.database.AppDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class DetailsActivity extends AppCompatActivity {
    private ImageView mPoster;
    private TextView mTitle;
    private TextView mSummary;
    RequestOptions options;
    private String mImageUrl;
    private String mTitleString;
    private String mSummaryString;
    private ImageView mStar;
    private int mStarvalue;
    private String mId;
    private AppDatabase mDb;
    private String saveImage;
    private String dataString;

    private static String TAG = "PermissionDemo";
    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
//        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
//        actionBar.setHomeButtonEnabled(true);
//         actionBar.setDisplayHomeAsUpEnabled(true);
//         getActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Details");

        mPoster = (ImageView) findViewById(R.id.Detail_photo);
        mTitle = (TextView) findViewById(R.id.Detail_title);
        mSummary = (TextView) findViewById(R.id.Detail_summary);
        mStar = (ImageView) findViewById(R.id.starImage);

        options = new RequestOptions().fitCenter().centerCrop().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground);

        Intent fetch = getIntent();
        mImageUrl = fetch.getStringExtra("poster");
        mTitleString = fetch.getStringExtra("title");
        mSummaryString = fetch.getStringExtra("summary");
        mStarvalue = Integer.parseInt(fetch.getStringExtra("starValue"));

        mId = fetch.getStringExtra("id");

        Glide.with(getApplicationContext()).load(mImageUrl).apply(options).into(mPoster);
        mTitle.setText(mTitleString);
        mSummary.setText(mSummaryString);
        if (mStarvalue == 1) {

            mStar.setImageResource(R.mipmap.star_full);

        }

        mStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStarvalue == 1) {
                    //true Star1 is full
                    mStarvalue = 0;
                    mDb.taskDao().delteByTitle(mTitleString);
                    mStar.setImageResource(R.mipmap.star_empty);
                } else {
                    mStarvalue = 1;
                    mStar.setImageResource(R.mipmap.star_full);
                     get();

//                    Anime_Model taskEntry = new Anime_Model(mId, mTitleString, mSummaryString, mStarvalue, dataString);
//                    // COMPLETED (9) Use the taskDao in the AppDatabase variable to insert the taskEntry
//                    if (dataString != null) {
//                        Log.d("datastring", dataString);
//                    }
//
//                    mDb.taskDao().insertTask(taskEntry);
//
//                    mStar.setImageResource(R.mipmap.star_full);
                }
            }
        });

        mDb = AppDatabase.getInstance(getApplicationContext());


    }
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                // Respond to the action bar's Up/Home button
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
        private void get () {
            //saveImage="jully";
            String url = mImageUrl;
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            Log.d("xxxxxxxxxx", url);
// Retrieves an image specified by the URL, displays it in the UI.
            ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {

                            saveImage = saveImageToInternalStorage(bitmap);
                            Log.d("saveImage", saveImage);
                            Anime_Model taskEntry = new Anime_Model(mId, mTitleString, mSummaryString, mStarvalue, saveImage);
                            // COMPLETED (9) Use the taskDao in the AppDatabase variable to insert the taskEntry
                            if (saveImage != null) {
                                Log.d("saveImage", saveImage);
                            }

                            mDb.taskDao().insertTask(taskEntry);


                        }
                    }, 0, 0, null, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {

                        }

                    });
            requestQueue.add(request);

        }

        private String saveImageToInternalStorage (Bitmap bitmapImage){

            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath = new File(directory, mTitleString+mId+".jpg");

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d("pathname", mypath.getAbsolutePath());
            return (mypath.getAbsolutePath());
        }

    }





