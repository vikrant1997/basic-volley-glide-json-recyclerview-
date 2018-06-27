package com.singh.vikrant.test1.database;

import android.app.Application;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

@Entity(tableName = "task")
public class Anime_Model {


    public Anime_Model(){
    }

   @NonNull @PrimaryKey(autoGenerate =false)
   @ColumnInfo(name = "ID")
    protected String mId;
    protected int mIdNext;
    @ColumnInfo(name = "TITLE")
    private String mTitle;
    private String mReleaseDate;
    private String mVoteAverage;
    private String mOverview;
@Ignore    private Uri mPosterUri;
    private String image_url;
   @Ignore private int star=0;

   @Ignore private AppDatabase mDb;

    @Ignore
    public Anime_Model(String id, String title, String releaseDate, String voteAverage, String
            overview, Uri posterUri) {

        mId = id;
        mTitle = title;
        mReleaseDate = releaseDate;
        mVoteAverage = voteAverage;
        mOverview = overview;
        mPosterUri = posterUri;
    }
//    @Ignore
//    public Anime_Model(String title,String overview){
//     mTitle=title;
//     mOverview=overview;
//     star=0;
//    }

    public Anime_Model(String mId,String title,String overview,int star){
        this.mId=mId;
        mTitle=title;
        mOverview=overview;
       // mPosterUri=posterUri;
        this.star=star;
        setStar(star);
    }

    public int getmIdNext(){
        return mIdNext;
    }
    public int setmIdNext(int mIdNext){
        this.mIdNext=mIdNext;
        return mIdNext;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getImage_url() {
        String send="http://image.tmdb.org/t/p/w500/"+image_url;
        return send;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

//    public Uri getPosterUri() {
//        return mPosterUri;
//    }

//    public void setPosterUri(Uri posterUri) {
//        mPosterUri = posterUri;
//    }
    public int getStarValue(){
         return star;

        }

    public void setStar(int star) {

            this.star = star;

    }
}