package com.singh.vikrant.test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.singh.vikrant.test1.database.Anime_Model;
import com.singh.vikrant.test1.database.AppDatabase;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        //android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        //actionBar.setHomeButtonEnabled(true);
       // actionBar.setDisplayHomeAsUpEnabled(true);
       // getActionBar().setDisplayHomeAsUpEnabled(true);

        mPoster=(ImageView)findViewById(R.id.Detail_photo);
        mTitle=(TextView)findViewById(R.id.Detail_title);
        mSummary=(TextView)findViewById(R.id.Detail_summary);
        mStar=(ImageView)findViewById(R.id.starImage);

        options=new RequestOptions().fitCenter().centerCrop().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground);

        Intent fetch=getIntent();
        mImageUrl=fetch.getStringExtra("poster");
        mTitleString=fetch.getStringExtra("title");
        mSummaryString=fetch.getStringExtra("summary");
        mStarvalue=Integer.parseInt(fetch.getStringExtra("starValue"));

        mId=fetch.getStringExtra("id");

        Glide.with(getApplicationContext()).load(mImageUrl).apply(options).into(mPoster);
        mTitle.setText(mTitleString);
        mSummary.setText(mSummaryString);
        if(mStarvalue==1){

            mStar.setImageResource(R.mipmap.star_full);
        }

        mStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mStarvalue==1){
                    //true Star1 is full
                    mStarvalue=0;
                    mStar.setImageResource(R.mipmap.star_empty);
                }else {
                    mStarvalue=1;

                    Anime_Model taskEntry = new Anime_Model(mId,mTitleString, mSummaryString,mStarvalue /*Uri.parse(mImageUrl)*/);
                    // COMPLETED (9) Use the taskDao in the AppDatabase variable to insert the taskEntry

                    mDb.taskDao().insertTask(taskEntry);

                    mStar.setImageResource(R.mipmap.star_full);
                }
            }
        });

        mDb = AppDatabase.getInstance(getApplicationContext());

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
