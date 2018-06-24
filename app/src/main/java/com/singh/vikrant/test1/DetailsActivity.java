package com.singh.vikrant.test1;

import android.app.ActionBar;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;

public class DetailsActivity extends AppCompatActivity {
    private ImageView mPoster;
    private TextView mTitle;
    private TextView mSummary;
    RequestOptions options;
    private String mImageUrl;
    private String mTitleString;
    private String mSummaryString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
       // getActionBar().setDisplayHomeAsUpEnabled(true);

        mPoster=(ImageView)findViewById(R.id.Detail_photo);
        mTitle=(TextView)findViewById(R.id.Detail_title);
        mSummary=(TextView)findViewById(R.id.Detail_summary);

        options=new RequestOptions().fitCenter().centerCrop().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground);

        Intent fetch=getIntent();
        mImageUrl=fetch.getStringExtra("poster");
        mTitleString=fetch.getStringExtra("title");
        mSummaryString=fetch.getStringExtra("summary");

        Glide.with(getApplicationContext()).load(mImageUrl).apply(options).into(mPoster);
        mTitle.setText(mTitleString);
        mSummary.setText(mSummaryString);

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
