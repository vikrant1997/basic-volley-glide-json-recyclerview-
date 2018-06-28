package com.singh.vikrant.test1.Fragmets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.singh.vikrant.test1.AnimeAdapter;
import com.singh.vikrant.test1.BookMarksAdapter;
import com.singh.vikrant.test1.DetailsActivity;
import com.singh.vikrant.test1.R;
import com.singh.vikrant.test1.database.Anime_Model;
import com.singh.vikrant.test1.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookMarks_Activity extends Fragment implements BookMarksAdapter.ListItemClickListener {


    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Anime_Model> animeList;
    private BookMarksAdapter mAnimeAdapter;
    private RecyclerView.Adapter adapter;
    private TextView mErrorMessageDisplay;
    private Context mContext;
    private AppDatabase mDb;

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.bookmarks_activity,null);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_numbers);
        animeList = new ArrayList<>();
        mErrorMessageDisplay = (TextView) view.findViewById(R.id.tv_error_message_display);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mContext=getActivity();
        mDb = AppDatabase.getInstance(getActivity());



        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAnimeAdapter = new BookMarksAdapter(mContext, animeList,this);

        mRecyclerView.setAdapter(mAnimeAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // COMPLETED (3) Call the adapter's setTasks method using the result
        // of the loadAllTasks method from the taskDao
        animeList = mDb.taskDao().loadAllTasks();
       mAnimeAdapter.setTasks(mDb.taskDao().loadAllTasks());

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String msg=String.valueOf(clickedItemIndex);
        Anime_Model obj=animeList.get(clickedItemIndex);

        String starValue;

        starValue=mDb.taskDao().loadStarValue(obj.getTitle());
        if(starValue!=null){
            starValue="1";
        }else{
            starValue=Integer.toString(obj.getStarValue());
        }
       // Toast.makeText(getActivity(),starValue,Toast.LENGTH_SHORT).show();
    String path;

            path = mDb.taskDao().getPath(animeList.get(clickedItemIndex).getTitle());
            Log.d("TEENTEN",path);

          //  Glide.with(mContext).load(path).apply(options).into(holder.mPosterView);





        Intent send=new Intent(mContext,DetailsActivity.class);
        send.putExtra("title",obj.getTitle());
        send.putExtra("poster",path);
        send.putExtra("summary",obj.getOverview());
        send.putExtra("starValue",starValue);
        send.putExtra("id",obj.getId());
        startActivity(send);

    }


}
