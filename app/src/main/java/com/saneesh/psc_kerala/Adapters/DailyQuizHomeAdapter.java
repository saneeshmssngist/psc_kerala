package com.saneesh.psc_kerala.Adapters;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saneesh.psc_kerala.Model.DailyQuiz;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Utils;

import java.util.ArrayList;
import java.util.Timer;

public class DailyQuizHomeAdapter extends RecyclerView.Adapter<DailyQuizHomeAdapter.DailyQuizHolder> {

    private Context context;
    private ArrayList<DailyQuiz> dailyQuizsArray;
    private DailyQuizListener dailyQuizListener;

    public interface DailyQuizListener {
        void datilyQuizTapped(int position);
    }

    public DailyQuizHomeAdapter(Context context, ArrayList<DailyQuiz> dailyQuizs, DailyQuizListener dailyQuizListener) {
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

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_ddaily_home, parent, false);
        return new DailyQuizHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DailyQuizHolder holder, final int position) {

        final DailyQuiz dailyQuiz = dailyQuizsArray.get(position);

        holder.txtTime.setText(dailyQuiz.getTime());

        if (dailyQuiz.getStatus().contentEquals("0")) {
            holder.handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    String nowDate = Utils.getTimeDifference(dailyQuiz.getDate());
                    if (!TextUtils.isEmpty(nowDate)) {
                        String[] strings = Utils.getTimeDifference(dailyQuiz.getDate()).split("-");
                        holder.txtViewHr.setText(strings[0]);
                        holder.txtViewMin.setText(strings[1]);
                        holder.txtViewSec.setText(strings[2]);

                        holder.txtData.setText(dailyQuiz.getData());

                    } else {
                        holder.layoutTimer.setVisibility(View.GONE);
                        holder.layoutQuiz.setVisibility(View.VISIBLE);

                        holder.txtData.setText(dailyQuiz.getData());
                    }

                    holder.handler.postDelayed(this, 1000);
                }
            }, 1000);

        } else {

            holder.txtData.setText(dailyQuiz.getData());
            holder.layoutTimer.setVisibility(View.GONE);
            holder.layoutQuiz.setVisibility(View.VISIBLE);
        }


        holder.layoutQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dailyQuizListener.datilyQuizTapped(position);
            }
        });
        holder.btnStart.setOnClickListener(new View.OnClickListener() {
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

        private TextView txtViewHr, txtViewMin, txtViewSec,txtTime,txtData;
        private CardView cardView;
        private Timer timer;
        private Handler handler;
        private RelativeLayout layoutQuiz;
        private RelativeLayout layoutTimer;
        private Button btnStart;

        public DailyQuizHolder(View itemView) {
            super(itemView);

            txtData = (TextView) itemView.findViewById(R.id.txtData);
            txtViewHr = (TextView) itemView.findViewById(R.id.txtViewHr);
            txtViewMin = (TextView) itemView.findViewById(R.id.txtViewMin);
            txtViewSec = (TextView) itemView.findViewById(R.id.txtViewSec);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            layoutQuiz = (RelativeLayout) itemView.findViewById(R.id.layoutQuiz);
            layoutTimer = (RelativeLayout) itemView.findViewById(R.id.layoutTimer);
            btnStart = (Button) itemView.findViewById(R.id.btnStart);

            timer = new Timer();
            handler = new Handler();

        }
    }
}
