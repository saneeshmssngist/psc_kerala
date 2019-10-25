package com.saneesh.psc_kerala.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saneesh.psc_kerala.R;

import java.util.List;

/**
 * Created by saNeesH on 2018-07-07.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicHolder> {

    Context context;
    List<String> topicContents;

    public TopicAdapter(Context context, List<String> topicModelsArray) {
        this.context = context;
        this.topicContents = topicModelsArray;
    }

    @Override
    public int getItemCount() {
        return topicContents.size();
    }

    @Override
    public TopicHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_topic_learn, null);

        return new TopicHolder(view);
    }

    @Override
    public void onBindViewHolder(TopicHolder holder, int position) {

        holder.txtContent.setText(topicContents.get(position));

    }

    public class TopicHolder extends RecyclerView.ViewHolder {
        TextView txtContent;

        public TopicHolder(View itemView) {
            super(itemView);

            txtContent = itemView.findViewById(R.id.txtContent);

        }
    }
}
