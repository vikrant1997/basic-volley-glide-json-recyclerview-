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
import com.singh.vikrant.test1.database.AppExecutors;

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
    private int mId;
    private AppDatabase mDb;
    private String saveImage;
    private String dataString;

    private static String TAG = "PermissionDemo";
    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

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

        mStarvalue=Integer.parseInt(fetch.getStringExtra("starValue"));
        Log.d("STAR",String.valueOf(mStarvalue));
        mId = fetch.getIntExtra("id",0);

        Glide.with(getApplicationContext()).load(mImageUrl).apply(options).into(mPoster);
        mTitle.setText(mTitleString);
        mSummary.setText(mSummaryString);
        if (mStarvalue==1) {
            mStar.setImageResource(R.mipmap.star_full);
        }

        mStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStarvalue==1) {
                    //true Star1 is full
                    mStarvalue = 0;
                   // mDb.taskDao().delteByTitle(mTitleString);
                    deleteItem(mTitleString);
                    mStar.setImageResource(R.mipmap.star_empty);
                } else {
                    mStarvalue =1;
                    mStar.setImageResource(R.mipmap.star_full);
                     get();

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
                            Anime_Model taskEntry = new Anime_Model(mId, mTitleString, mSummaryString, Integer.toString(mStarvalue), saveImage);
                            Log.d("starValue", String.valueOf(mStarvalue));
                            if (saveImage != null) {
                                Log.d("saveImage", saveImage);
                            }

                           // mDb.taskDao().insertTask(taskEntry);
                            insertItem(taskEntry);
                            finish();

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
            File mypath = new File(directory, mId+".jpg");

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

        public void insertItem(final Anime_Model taskAnime){
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {

                        mDb.taskDao().insertTask(taskAnime);



                }
            });
        }
        public void deleteItem(String name){
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.taskDao().delteByTitle(mTitleString);
                    finish();
                }
            });
        }

    }





