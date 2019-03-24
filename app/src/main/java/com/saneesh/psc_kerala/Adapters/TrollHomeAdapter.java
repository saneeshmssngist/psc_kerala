package com.saneesh.psc_kerala.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saneesh.psc_kerala.Activities.TrollsHomeActivity;
import com.saneesh.psc_kerala.Model.TrollHome;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

public class TrollHomeAdapter extends RecyclerView.Adapter<TrollHomeAdapter.TrollHomeHolder>
{

    private Context context;
    private ArrayList<TrollHome> trollHomesArray;
    private TrollHomeListener trollHomeListener;

    public void update(ArrayList<TrollHome> trollHomesArray) {
        this.trollHomesArray = trollHomesArray;
        notifyDataSetChanged();
    }

    public interface TrollHomeListener
    {
        void onItemTapped(int position);
    }

    public TrollHomeAdapter(Context context, ArrayList<TrollHome> stringArrayList,TrollHomeListener trollHomeListener)
    {
        this.context = context;
        this.trollHomesArray = stringArrayList;
        this.trollHomeListener = trollHomeListener;
    }

    @Override
    public int getItemCount() {
        return trollHomesArray.size();
    }

    @NonNull
    @Override
    public TrollHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.lsit_item_troll_home,parent,false);
        return new TrollHomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrollHomeHolder holder, final int position) {

        holder.txtViewName.setText(trollHomesArray.get(position).getName());
        holder.txtViewDate.setText(trollHomesArray.get(position).getDate());
        holder.txtViewNo.setText(trollHomesArray.get(position).getContentSize()+" Trolls");

        Glide
                .with(context)
                .load(trollHomesArray.get(position).getFirstUrl())
                .into(holder.imgView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trollHomeListener.onItemTapped(position);
            }
        });

    }

    public class TrollHomeHolder extends RecyclerView.ViewHolder {

        private TextView txtViewName,txtViewNo,txtViewDate;
        private ImageView imgView;
        private CardView cardView;

        public TrollHomeHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            txtViewName = (TextView) itemView.findViewById(R.id.txtViewName);
            txtViewNo = (TextView) itemView.findViewById(R.id.txtViewNo);
            txtViewDate = (TextView) itemView.findViewById(R.id.txtViewDate);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);

        }
    }
}
