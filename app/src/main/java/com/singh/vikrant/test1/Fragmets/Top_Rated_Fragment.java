package com.singh.vikrant.test1.Fragmets;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.singh.vikrant.test1.AnimeAdapter;
import com.singh.vikrant.test1.Constants;
import com.singh.vikrant.test1.DetailsActivity;
import com.singh.vikrant.test1.R;
import com.singh.vikrant.test1.database.Anime_Model;
import com.singh.vikrant.test1.database.AppDatabase;
import com.singh.vikrant.test1.database.AppExecutors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Top_Rated_Fragment extends Fragment implements AnimeAdapter.ListItemClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Anime_Model> animeListTop;
    private TextView mErrorMessageDisplay;
    private AnimeAdapter mAnimeAdapterTop;
    private ProgressBar mLoadingIndicator;
    private String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=6256134f8d005821fcb0ca17a719cd85";
    private String nexturl;
    private Context mContext;
    private String mStar;

    private AppDatabase mDb;

    public Top_Rated_Fragment(){

    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.top_rated_activity,null);
        return root;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_numbers_top);
        animeListTop = new ArrayList<>();
        mErrorMessageDisplay = (TextView) view.findViewById(R.id.tv_error_message_display_top);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//        mLoadingIndicator = (ProgressBar) view.findViewById(R.id.pb_loading_indicator);

        mErrorMessageDisplay = (TextView) view.findViewById(R.id.tv_error_message_display_top);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mContext=getActivity();

        getData();
    }



    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        mDb = AppDatabase.getInstance(getActivity());

        for (int i = 1; i < 5; i++) {
            String num = String.valueOf(i);
            nexturl = url + "&page=" + num;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,

                    nexturl, null, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {
                    JSONArray jsonAnimeArray = null;
                    try {
                        jsonAnimeArray = response.getJSONArray(Constants.JSON_LIST);
                        for (int i = 0; i < jsonAnimeArray.length(); i++) {

                            JSONObject jsonObject = jsonAnimeArray.getJSONObject(i);
                            Anime_Model anime = new Anime_Model();
                            anime.setId(Integer.parseInt(jsonObject.getString(Constants.JSON_ID)));
                            anime.setTitle(jsonObject.getString(Constants.JSON_TITLE));
                            anime.setReleaseDate(jsonObject.getString(Constants.JSON_RELEASE_DATE));
                            anime.setVoteAverage(jsonObject.getString(Constants.JSON_VOTE_AVERAGE));
                            anime.setOverview(jsonObject.getString(Constants.JSON_OVERVIEW));
                            anime.setImage_url(jsonObject.getString(Constants.JSON_POSTER_PATH));

                            animeListTop.add(anime);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }

                        progressDialog.dismiss();
                    setAnimeadapter(animeListTop);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", error.toString());
                    progressDialog.dismiss();

                    mErrorMessageDisplay.setVisibility(View.VISIBLE);
                }
            });
            jsonObjectRequest.setShouldCache(false);
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.getCache().clear();
            jsonObjectRequest.setShouldCache(false);

            requestQueue.add(jsonObjectRequest);
        }

    }

    public void setAnimeadapter(List<Anime_Model> lst) {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAnimeAdapterTop = new AnimeAdapter(mContext, lst,this);
        mRecyclerView.setAdapter(mAnimeAdapterTop);


    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        final Anime_Model obj=animeListTop.get(clickedItemIndex);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {

            @Override
            public void run() {
                final String task = mDb.taskDao().loadStarValue(obj.getId());
                if (task == null) {
                    mStar="0";
                } else {
                    mStar=task;
                }

                Log.d("popStar",mStar);
                Intent send=new Intent(mContext,DetailsActivity.class);
                send.putExtra("title",obj.getTitle());
                send.putExtra("poster",obj.getImage_url());
                send.putExtra("summary",obj.getOverview());
                send.putExtra("starValue",mStar);
                send.putExtra("id",obj.getId());
                startActivity(send);
            }

        });
    }

//    public String loadStar(final int id) {
//
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                final String task = mDb.taskDao().loadStarValue(id);
//                if (task==null)mStar="0";
//                mStar=task;
//
//            }
//        });
//        return mStar;
//    }

}



