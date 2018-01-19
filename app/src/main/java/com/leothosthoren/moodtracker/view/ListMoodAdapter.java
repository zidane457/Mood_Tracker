package com.leothosthoren.moodtracker.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.ListMoodItem;

import java.util.ArrayList;

/**
 * Created by Sofiane M. alias Leothos Thoren on 15/01/2018
 */
public class ListMoodAdapter extends RecyclerView.Adapter<ListMoodAdapter.ListMoodViewHolder> {

    private static final int NUMBER_ITEM = 8;
    private ArrayList<ListMoodItem> mListMoodItems;
    private OnItemClickListener mListener;
    private Context mContext;

    public ListMoodAdapter(ArrayList<ListMoodItem> listMoodItems, Context context) {
        mListMoodItems = listMoodItems;
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ListMoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        view.getLayoutParams().height = parent.getHeight() / mListMoodItems.size();
        ;
        ListMoodViewHolder lmvh = new ListMoodViewHolder(view, mListener);
        return lmvh;
    }

    @Override
    public void onBindViewHolder(ListMoodViewHolder holder, int position) {
        final ListMoodItem currentItem = mListMoodItems.get(position);

        holder.mFrameLayout.setBackgroundResource(currentItem.getColor());
        holder.mTextView.setText(currentItem.getDate(position));
        holder.mImageViewComment.setImageResource(currentItem.getBtnComment());
        holder.mToastView.setText(currentItem.getComment());

        if (currentItem.getComment().equals(""))
            holder.mImageViewComment.setVisibility(View.GONE);

        //Gestion du layout
        int devicewidth;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //if you need three fix imageview in width
         devicewidth = (displaymetrics.widthPixels * 20 * (currentItem.getSmileyValue() + 1)) / 100;

//        //if you need 4-5-6 anything fix imageview in height
//        int deviceheight = displaymetrics.heightPixels / 4;

        holder.mFrameLayout.getLayoutParams().width = devicewidth;

    }

    @Override
    public int getItemCount() {
        if (mListMoodItems.size() == NUMBER_ITEM)
            mListMoodItems.remove(0);
        return mListMoodItems.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ListMoodViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout mFrameLayout;
        public TextView mTextView;
        public ImageView mImageViewComment;
        public TextView mToastView;


        private ListMoodViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mFrameLayout = itemView.findViewById(R.id.item_history_layout);
            mTextView = itemView.findViewById(R.id.item_history_text);
            mImageViewComment = itemView.findViewById(R.id.item_history_commentBtn);
            mToastView = itemView.findViewById(R.id.item_history_toast);


            mImageViewComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

}
