package com.saneesh.psc_kerala.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saneesh.psc_kerala.Activities.TopicActivity;
import com.saneesh.psc_kerala.Model.TopicModel;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-07-07.
 */

public class TopicHomeAdapter extends RecyclerView.Adapter<TopicHomeAdapter.TopicHolder> {

    Context context;
    ArrayList<TopicModel> topicModelsArray;

    public TopicHomeAdapter(Context context, ArrayList<TopicModel> topicModelsArray) {
        this.context = context;
        this.topicModelsArray = topicModelsArray;
    }

    @Override
    public int getItemCount() {
        return topicModelsArray.size();
    }

    @Override
    public TopicHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_topic_home, null);

        return new TopicHolder(view);
    }

    @Override
    public void onBindViewHolder(TopicHolder holder, int position) {

        TopicModel topicModel = topicModelsArray.get(position);
        holder.txtContentHome.setText(topicModel.getTopicName());

        Glide
                .with(context)
                .load(topicModel.getImageUrl())
                .into(holder.imageView);

    }

    public class TopicHolder extends RecyclerView.ViewHolder {
        TextView txtContentHome;
        ImageView imageView;

        public TopicHolder(View itemView) {
            super(itemView);
            txtContentHome = itemView.findViewById(R.id.txtContentHome);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, TopicActivity.class);
                    intent.putExtra("contents_data",topicModelsArray.get(getAdapterPosition()));
                    context.startActivity(intent);

                }
            });
        }
    }
}
