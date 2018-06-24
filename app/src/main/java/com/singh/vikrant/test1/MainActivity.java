package com.singh.vikrant.test1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AnimeAdapter.ListItemClickListener{

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Anime_Model> animeList;
    private RecyclerView.Adapter adapter;
    private TextView mErrorMessageDisplay;
    private AnimeAdapter mAnimeAdapter;
    private ProgressBar mLoadingIndicator;
    private String url = "https://api.themoviedb.org/3/movie/popular?api_key=6256134f8d005821fcb0ca17a719cd85";
    private String nexturl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_numbers);
        animeList = new ArrayList<>();
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);





        getData();
    }




//    @Override
//    public void onDestroy() {
//        super.onDestroy();
////                mAnimeAdapter.remove(animeList);
////                mAnimeAdapter=null;
////                mRecyclerView=null;
////              Runtime.getRuntime().gc();
//        Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
//        animeList.clear();
//        mRecyclerView = null;
//       // mRecyclerView.setAdapter(null);
//        animeList.clear();
//        mAnimeAdapter.remove(animeList);
//        Glide.get(this).clearMemory();
//        try {
//            trimCache(this);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        for (int i = 1; i <= 5; i++) {
            String num = String.valueOf(i);
            nexturl = url + "&page=" + num;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,

                    nexturl, null, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {
                    //animeList.clear();
                    JSONArray jsonAnimeArray = null;
                    try {
                        jsonAnimeArray = response.getJSONArray(Constants.JSON_LIST);
                        for (int i = 0; i < jsonAnimeArray.length(); i++) {

                            JSONObject jsonObject = jsonAnimeArray.getJSONObject(i);
                            Anime_Model anime = new Anime_Model();
                            anime.setId(jsonObject.getString(Constants.JSON_ID));
                            anime.setTitle(jsonObject.getString(Constants.JSON_TITLE));
                            anime.setReleaseDate(jsonObject.getString(Constants.JSON_RELEASE_DATE));
                            anime.setVoteAverage(jsonObject.getString(Constants.JSON_VOTE_AVERAGE));
                            anime.setOverview(jsonObject.getString(Constants.JSON_OVERVIEW));
                            anime.setImage_url(jsonObject.getString(Constants.JSON_POSTER_PATH));
//                                                Uri posterUri = createPosterUri(jsonAnimeArray.getJSONObject(i).getString
//                                                        (Constants.JSON_POSTER_PATH));

                            animeList.add(anime);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }

                    Toast.makeText(MainActivity.this, "Size of Liste " + String.valueOf(animeList.size()), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(MainActivity.this,animeList.get(1).toString(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    setAnimeadapter(animeList);
                    //    adapter.notifyDataSetChanged();
                    //                               progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", error.toString());
                    progressDialog.dismiss();

                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    mErrorMessageDisplay.setVisibility(View.VISIBLE);
                }
            });
            jsonObjectRequest.setShouldCache(false);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.getCache().clear();
            jsonObjectRequest.setShouldCache(false);

            requestQueue.add(jsonObjectRequest);
        }


    }

    public void setAnimeadapter(List<Anime_Model> lst) {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);


        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAnimeAdapter = new AnimeAdapter(this, lst,this);

        mRecyclerView.setAdapter(mAnimeAdapter);


    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String msg=String.valueOf(clickedItemIndex);
        Anime_Model obj=animeList.get(clickedItemIndex);

//        Toast.makeText(this,obj.getTitle(),Toast.LENGTH_SHORT).show();
        Intent send=new Intent(this,DetailsActivity.class);
        send.putExtra("title",obj.getTitle());
        send.putExtra("poster",obj.getImage_url());
        send.putExtra("summary",obj.getOverview());
        startActivity(send);
    }
}