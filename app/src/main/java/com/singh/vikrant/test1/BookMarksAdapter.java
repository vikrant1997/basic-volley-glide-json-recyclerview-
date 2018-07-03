package com.singh.vikrant.test1;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.singh.vikrant.test1.database.Anime_Model;
import com.singh.vikrant.test1.database.AppDatabase;

import java.util.List;

public class BookMarksAdapter extends RecyclerView.Adapter<BookMarksAdapter.NumberViewHolder> {

    RequestOptions options;
    private Context mContext;
    private static final String TAG = BookMarksAdapter.class.getSimpleName();
    final private ListItemClickListener mOnClickListener;

    private AppDatabase mDb;

    private List<Anime_Model> mListItems;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    // COMPLETED (4) Add a ListItemClickListener as a parameter to the constructor and store it in mOnClickListener

    public BookMarksAdapter(Context mContext, List list, BookMarksAdapter.ListItemClickListener listener) {
        mOnClickListener = listener;
        this.mContext = mContext;
        this.mListItems = list;
        mDb = AppDatabase.getInstance(mContext);
        options = new RequestOptions().fitCenter().centerCrop().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground);
        // options.format(DecodeFormat.PREFER_RGB_565);

    }

    public void setTasks(List<Anime_Model> taskEntries) {
        mListItems = taskEntries;
        notifyDataSetChanged();
    }

    public void getTaskStarValue(List<Anime_Model> taskEntries) {

    }

    public void remove(List<Anime_Model> item) {
        int position = mListItems.indexOf(item);
        mListItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public BookMarksAdapter.NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_layout;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new BookMarksAdapter.NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookMarksAdapter.NumberViewHolder holder, int position) {

        // holder.mTitle.setText(mListItems.get(position).getTitle());
        if (mDb != null) {
            //String path = mDb.taskDao().getPath(mListItems.get(position).getTitle());
            String path = mListItems.get(position).getImagePath();
            holder.mTitle.setText(mListItems.get(position).getTitle());
            //Glide.with(mContext).load(Uri.fromFile(new File(path))).apply(options).into(holder.mPosterView);
            Glide.with(mContext).load(path).apply(options).into(holder.mPosterView);

        }

    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

}


