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

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.NumberViewHolder> {

    RequestOptions options;
    private Context mContext;
    private static final String TAG = AnimeAdapter.class.getSimpleName();
   // final private ListItemClickListener mOnClickListener;

    private List<Anime_Model> mListItems;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    // COMPLETED (4) Add a ListItemClickListener as a parameter to the constructor and store it in mOnClickListener

    public AnimeAdapter(Context mContext, List list) {
       this.mContext=mContext;
       this.mListItems=list;
       options=new RequestOptions().fitCenter().centerCrop().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground);
    }


        public void remove(List<Anime_Model> item) {
            int position = mListItems.indexOf(item);
            mListItems.remove(position);
            notifyItemRemoved(position);
        }

    /*
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.*
     */
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


//        Glide.with(mContext).load("http://image.tmdb.org/t/p/w780/"+holder.mItem.getPosterUri());
//               // .dontTransform().into(holder.mPosterView);

       // holder.mTitle.setText(holder.mItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

// COMPLETED (5) Implement OnClickListener in the NumberViewHolder class

    /**
     * Cache of the children views for a list item.
     */
    class NumberViewHolder extends RecyclerView.ViewHolder {
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
        }



    }

        }

