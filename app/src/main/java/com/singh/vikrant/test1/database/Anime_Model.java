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

    @Ignore
    public Anime_Model(){
    }

   @NonNull @PrimaryKey(autoGenerate =true)
   @ColumnInfo(name = "ID")
    protected int mId;
    @ColumnInfo(name = "TITLE")
    private String mTitle;
    private String mReleaseDate;
    private String mVoteAverage;
    private String mOverview;
    @Ignore   private Uri mPosterUri;
    @ColumnInfo(name="IMAGE_PATH")
    private String ImagePath;
    private String image_url;

    public String star="0";


    public Anime_Model(int mId,String title,String overview,String star,String ImagePath){
        this.mId=mId;
        mTitle=title;
        mOverview=overview;
        this.star=star;
        this.ImagePath=ImagePath;
    }

    public String getImagePath(){
        return ImagePath;
    }
    public void setImagePath(String ImagePath){
        this.ImagePath=ImagePath;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getImage_url() {
        String send="http://image.tmdb.org/t/p/w500/"+image_url;
        return send;}


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

    public String getStarValue(){
         return star;
        }

    public void setStar(String star) {
            this.star = star;

    }
}