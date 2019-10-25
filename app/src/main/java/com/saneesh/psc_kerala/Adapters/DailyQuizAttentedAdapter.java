package com.saneesh.psc_kerala.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saneesh.psc_kerala.Model.DailyQuiz;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

public class DailyQuizAttentedAdapter extends RecyclerView.Adapter<DailyQuizAttentedAdapter.DailyQuizHolder> {

    private Context context;
    private ArrayList<DailyQuiz> dailyQuizsArray;
    private DailyQuizListener dailyQuizListener;

    public interface DailyQuizListener {
        void datilyQuizTapped(int position);
    }

    public DailyQuizAttentedAdapter(Context context, ArrayList<DailyQuiz> dailyQuizs, DailyQuizListener dailyQuizListener) {
        this.context = context;
        this.dailyQuizsArray = dailyQuizs;
        this.dailyQuizListener = dailyQuizListener;
    }

    @Override
    public int getItemCount() {
        return dailyQuizsArray.size();
    }

    @NonNull
    @Override
    public DailyQuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_daily_quiz, parent, false);
        return new DailyQuizHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DailyQuizHolder holder, final int position) {

        final DailyQuiz dailyQuiz = dailyQuizsArray.get(position);

        holder.txtTime.setText(dailyQuiz.getTime());
        holder.txtData.setText(dailyQuiz.getData());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dailyQuizListener.datilyQuizTapped(position);
            }
        });

    }

    public void update(ArrayList<DailyQuiz> dailyQuizsArray) {
        this.dailyQuizsArray = dailyQuizsArray;
        notifyDataSetChanged();
    }

    public class DailyQuizHolder extends RecyclerView.ViewHolder {

        private TextView txtTime,txtData;
        private CardView cardView;

        public DailyQuizHolder(View itemView) {
            super(itemView);

            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtData = (TextView) itemView.findViewById(R.id.txtData);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

        }
    }
}
