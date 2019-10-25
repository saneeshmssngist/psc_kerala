package com.saneesh.psc_kerala.Adapters;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saneesh.psc_kerala.Model.DailyQuiz;
import com.saneesh.psc_kerala.Model.DailyQuizData;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Utils;

import java.util.ArrayList;
import java.util.Timer;

public class DailyQuizHomeAdapter extends RecyclerView.Adapter<DailyQuizHomeAdapter.DailyQuizHolder> {

    private Context context;
    private ArrayList<DailyQuizData> dailyQuizsArray;

    public interface DailyQuizListener {
        void datilyQuizTapped(int position);
    }

    public DailyQuizHomeAdapter(Context context, ArrayList<DailyQuizData> dailyQuizs) {
        this.context = context;
        this.dailyQuizsArray = dailyQuizs;
    }

    @Override
    public int getItemCount() {
        return dailyQuizsArray.size();
    }

    @NonNull
    @Override
    public DailyQuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_ddaily_home, parent, false);
        return new DailyQuizHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DailyQuizHolder holder, final int position) {

        final DailyQuizData dailyQuiz = dailyQuizsArray.get(position);
        holder.txtTime.setText(dailyQuiz.getTime());
        holder.txtDate.setText(dailyQuiz.getDate());
        holder.txtCorrect.setText(dailyQuiz.getScore());
        holder.txtWrong.setText(dailyQuiz.getWrong());

    }


    public class DailyQuizHolder extends RecyclerView.ViewHolder {

        private TextView txtTime,txtDate,txtCorrect,txtWrong;

        public DailyQuizHolder(View itemView) {
            super(itemView);

            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtCorrect = (TextView) itemView.findViewById(R.id.txtCorrect);
            txtWrong = (TextView) itemView.findViewById(R.id.txtWrong);

        }
    }
}
