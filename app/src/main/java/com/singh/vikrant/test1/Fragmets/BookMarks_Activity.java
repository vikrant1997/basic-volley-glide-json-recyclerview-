package com.singh.vikrant.test1.Fragmets;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private int mStar;

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.popular_acitivity,null);
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
        retrieveTasks();
    }

    private void retrieveTasks() {
        final LiveData<List<Anime_Model>> tasks = mDb.taskDao().loadAllTasks();
        tasks.observe(this, new Observer<List<Anime_Model>>() {
            @Override
            public void onChanged(@Nullable List<Anime_Model> taskEntries) {
                animeList=taskEntries;
                mAnimeAdapter.setTasks(taskEntries);
            }
        });

   }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String msg=String.valueOf(clickedItemIndex);
        Anime_Model obj=animeList.get(clickedItemIndex);

        String starValue;

        //starValue=loadStar(obj.getTitle());
        starValue=obj.getStarValue();

    String path;

        //    path = mDb.taskDao().getPath(animeList.get(clickedItemIndex).getTitle());
         path = obj.getImagePath();


        Intent send=new Intent(mContext,DetailsActivity.class);
        send.putExtra("title",obj.getTitle());
        send.putExtra("poster",path);
        send.putExtra("summary",obj.getOverview());
        send.putExtra("starValue",starValue);
        send.putExtra("id",obj.getId());
        startActivity(send);

    }


}
