package com.example.myapplication.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<MatchExampleItem> mExampleList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public CircularImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public CardView cv;
        RelativeLayout rel;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);

            mImageView = itemView.findViewById(R.id.img);
            mTextView1 = itemView.findViewById(R.id.kills);
            mTextView2 = itemView.findViewById(R.id.deaths);
            cv = itemView.findViewById(R.id.cardid);
            rel = itemView.findViewById(R.id.relid);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public ExampleAdapter(ArrayList<MatchExampleItem> exampleItems) {
        this.mExampleList = exampleItems;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_view, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);


        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        MatchExampleItem currentItem = mExampleList.get(position);

        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getmText2());


        if(currentItem.getmText1().equals("10"))
        {
            holder.rel.setBackgroundResource(R.drawable.gradient_color_lose);
        }
        else
        {
            holder.rel.setBackgroundResource(R.drawable.gradient_color_win);
        }

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
