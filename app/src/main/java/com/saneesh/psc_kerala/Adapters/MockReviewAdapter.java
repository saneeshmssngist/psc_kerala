package com.saneesh.psc_kerala.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saneesh.psc_kerala.Activities.MockReviewActivity;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.Model.QuizTable;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-07-02.
 */

public class MockReviewAdapter extends RecyclerView.Adapter<MockReviewAdapter.ReviewHolder> {

    private ArrayList<QuizTable> questionDatasArray;
    private ArrayList<String> answerArray;
    private Context context;

    public MockReviewAdapter(Context context, ArrayList<QuizTable> questionDatasArray, ArrayList<String> answerArray)
    {
        this.questionDatasArray = questionDatasArray;
        this.answerArray = answerArray;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return questionDatasArray.size();
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_mock_review,null);

        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {

        QuizTable questionsModel = questionDatasArray.get(position);

        holder.txtViewNo.setText("Q"+String.valueOf(position+1));
        holder.txtViewQuestion.setText(questionsModel.getQuestion());

        if(questionsModel.getAnswer().equals("1"))
            holder.txtViewAnswr.setText(questionsModel.getOption1());
        else if(questionsModel.getAnswer().equals("2"))
            holder.txtViewAnswr.setText(questionsModel.getOption2());
        else if(questionsModel.getAnswer().equals("3"))
            holder.txtViewAnswr.setText(questionsModel.getOption3());
        else
            holder.txtViewAnswr.setText(questionsModel.getOption4());

        holder.txtViewAnswr.setTextColor(Color.parseColor("#088643"));

        if(answerArray.get(position).equals("1"))
            holder.txtViewUserAnswr.setText(questionsModel.getOption1());
        else if(answerArray.get(position).equals("2"))
            holder.txtViewUserAnswr.setText(questionsModel.getOption2());
        else if(answerArray.get(position).equals("3"))
            holder.txtViewUserAnswr.setText(questionsModel.getOption3());
        else
            holder.txtViewUserAnswr.setText(questionsModel.getOption4());

        if(questionsModel.getAnswer().equals(answerArray.get(position)))
        {
            holder.txtViewUserAnswr.setTextColor(Color.parseColor("#088643"));
        }
        else
        {
            holder.txtViewUserAnswr.setTextColor(Color.parseColor("#c20104"));
        }


    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        TextView txtViewNo,txtViewQuestion,txtViewUserAnswr,txtViewAnswr;

        public ReviewHolder(View itemView) {
            super(itemView);

            txtViewNo = itemView.findViewById(R.id.txtViewNo);
            txtViewQuestion = itemView.findViewById(R.id.txtViewQuestion);
            txtViewUserAnswr = itemView.findViewById(R.id.txtViewUserAnswr);
            txtViewAnswr = itemView.findViewById(R.id.txtViewAnswr);

        }

    }
}
