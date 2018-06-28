package com.singh.vikrant.test1;

import android.content.Context;
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

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.NumberViewHolder> {

    RequestOptions options;
    private Context mContext;
    private static final String TAG = AnimeAdapter.class.getSimpleName();
    final private ListItemClickListener mOnClickListener;


    private List<Anime_Model> mListItems;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    // COMPLETED (4) Add a ListItemClickListener as a parameter to the constructor and store it in mOnClickListener

    public AnimeAdapter(Context mContext, List list,ListItemClickListener listener) {
        mOnClickListener=listener;
       this.mContext=mContext;
       this.mListItems=list;
       options=new RequestOptions().fitCenter().centerCrop().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground);
      // options.format(DecodeFormat.PREFER_RGB_565);

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
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        //boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);


        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {

        holder.mTitle.setText(mListItems.get(position).getTitle());
        Glide.with(mContext).load(mListItems.get(position).getImage_url()).apply(options).into(holder.mPosterView);


//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

// COMPLETED (5) Implement OnClickListener in the NumberViewHolder class

    /**
     * Cache of the children views for a list item.
     */
    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //    // Will display the position in the list, ie 0 through getItemCount() - 1
//    TextView listItemNumberView;
//    // Will display which ViewHolder is displaying this data
//    TextView viewHolderIndex;
//
//    /**
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
//
//    @Override
//    public void onViewRecycled(@NonNull NumberViewHolder holder) {
//       // super.onViewRecycled(holder);
//       Glide.with(holder.mPosterView).clear(holder.mPosterView);
//    }

}

