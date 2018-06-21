package com.singh.vikrant.test1;

import android.net.Uri;

public class Anime_Model {

    public Anime_Model(){

    }
//    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
//            public Anime_Model createFromParcel(Parcel in) {
//                return new Anime_Model(in);
//            }
//
//        public Anime_Model[] newArray(int size) {
//            return new Anime_Model[size];
//        }
//    };
    private String mId;
    private String mTitle;
    private String mReleaseDate;
    private String mVoteAverage;
    private String mOverview;
    private Uri mPosterUri;
    private String image_url;

    public Anime_Model(String id, String title, String releaseDate, String voteAverage, String
            overview, Uri posterUri) {
        mId = id;
        mTitle = title;
        mReleaseDate = releaseDate;
        mVoteAverage = voteAverage;
        mOverview = overview;
        mPosterUri = posterUri;
    }

//    public Anime_Model(Parcel in) {
//        mId = in.readString();
//        mTitle = in.readString();
//        mReleaseDate = in.readString();
//        mVoteAverage = in.readString();
//        mOverview = in.readString();
//        mPosterUri = (Uri) in.readValue(Movie.class.getClassLoader());
//    }

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
        String send="http://image.tmdb.org/t/p/w780/"+image_url;
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

    public Uri getPosterUri() {
        return mPosterUri;
    }

    public void setPosterUri(Uri posterUri) {
        mPosterUri = posterUri;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int i) {
//        dest.writeString(mId);
//        dest.writeString(mTitle);
//        dest.writeString(mReleaseDate);
//        dest.writeString(mVoteAverage);
//        dest.writeString(mOverview);
//        dest.writeValue(mPosterUri);
//    }
}