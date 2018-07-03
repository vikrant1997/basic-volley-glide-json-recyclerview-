package com.singh.vikrant.test1;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.singh.vikrant.test1.database.Anime_Model;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RequestOptions options;
    private Context mContext;
    private static final String TAG = AnimeAdapter.class.getSimpleName();
    final private ListItemClickListener mOnClickListener;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    private List<Anime_Model> mListItems;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public AnimeAdapter(Context mContext, List list,ListItemClickListener listener) {
        mOnClickListener=listener;
       this.mContext=mContext;
       this.mListItems=list;
       options=new RequestOptions().fitCenter().centerCrop().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground);

    }

    public void setTasks(List<Anime_Model> taskEntries) {
        mListItems = taskEntries;
        notifyDataSetChanged();
    }

    public void getTaskStarValue(List<Anime_Model> taskEntries){

    }
        public void remove(List<Anime_Model> item) {
            int position = mListItems.indexOf(item);
            mListItems.remove(position);
            notifyItemRemoved(position);
        }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_layout;
        LayoutInflater inflater = LayoutInflater.from(context);

        if(viewType==ITEM){
            View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

            return new NumberViewHolder(view);
        }else {
            View v2 = inflater.inflate(R.layout.item_progress, viewGroup, false);
            LoadingVH loading = new LoadingVH(v2);
            return loading;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                NumberViewHolder animeHolder = (NumberViewHolder) holder;

                animeHolder.mTitle.setText(mListItems.get(position).getTitle());
                Glide.with(mContext).load(mListItems.get(position).getImage_url()).apply(options).into(animeHolder.mPosterView);
                break;
            case LOADING:
//                Do nothing
                break;
        }

    }


    @Override
    public int getItemViewType(int position) {
        return (position == mListItems.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    @Override
    public int getItemCount() {
        return mListItems==null?0:mListItems.size();
    }

    //--------------------------HELPERS------------------

    public void add(Anime_Model mc) {
        mListItems.add(mc);
        notifyItemInserted(mListItems.size() - 1);
    }

    public void addAll(List<Anime_Model> mcList) {
        for (Anime_Model mc : mcList) {
            add(mc);
        }
    }

    public void remove(Anime_Model city) {
        int position = mListItems.indexOf(city);
        if (position > -1) {
            mListItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Anime_Model());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mListItems.size() - 1;
        Anime_Model item = getItem(position);

        if (item != null) {
            mListItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Anime_Model getItem(int position) {
        return mListItems.get(position);
    }

    //--------------------------HELPERS------------------

// COMPLETED (5) Implement OnClickListener in the NumberViewHolder class

    /**
     * Cache of the children views for a list item.
     */
    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public Anime_Model mItem;

        public final View mView;
        public final ImageView mPosterView;
        public final TextView mTitle;


        /* Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         * @param itemView The View that you inflated in
         *                 {@link AnimeAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public NumberViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            mPosterView = (ImageView) itemView.findViewById(R.id.poster);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int clickedPosition=getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}

