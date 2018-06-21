package com.singh.vikrant.test1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

        private RecyclerView mList;
        private LinearLayoutManager linearLayoutManager;
        private List<Anime_Model> animeList;
        private RecyclerView.Adapter adapter;
        private TextView mErrorMessageDisplay;
        private AnimeAdapter mAnimeAdapter;
        private ProgressBar mLoadingIndicator;
        private String url="https://api.themoviedb.org/3/movie/popular?api_key=6256134f8d005821fcb0ca17a719cd85";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                mList = (RecyclerView) findViewById(R.id.rv_numbers);
                animeList=new ArrayList<>();
                mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
                 linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);


                getData();
        }

        private void getData() {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,

                        url,null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                                JSONArray jsonAnimeArray=null;
                                try {
                                        jsonAnimeArray=response.getJSONArray(Constants.JSON_LIST);
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

                            Toast.makeText(MainActivity.this,"Size of Liste "+String.valueOf(animeList.size()),Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this,animeList.get(1).toString(),Toast.LENGTH_SHORT).show();
                                setAnimeadapter(animeList);
//                                adapter.notifyDataSetChanged();
 //                               progressDialog.dismiss();
                        }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                                Log.e("Volley", error.toString());
                                progressDialog.dismiss();
                        }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(jsonObjectRequest);
        }

        public void setAnimeadapter (List<Anime_Model> lst) {

                mList.setLayoutManager(linearLayoutManager);
                mList.setHasFixedSize(true);

                mAnimeAdapter = new AnimeAdapter(this,lst);

                mList.setAdapter(mAnimeAdapter);


        }
}