package com.saneesh.psc_kerala.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.saneesh.psc_kerala.Activities.QuestionPaperActivity;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.QuestionPaperHome;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-07-06.
 */

public class QuestionHomeAdapter extends RecyclerView.Adapter<QuestionHomeAdapter.QuestionHolder> {

    private ArrayList<QuestionPaperHome> paperNames;
    private Context context;
    private QuestionsInterface questionsInterface;

    public void update(ArrayList<QuestionPaperHome> questionPaperHomesArray) {
        this.paperNames = questionPaperHomesArray;
        notifyDataSetChanged();
    }

    public interface QuestionsInterface
    {
        void questionTapped(int adapterPosition);
    }

    public QuestionHomeAdapter(Context context, ArrayList<QuestionPaperHome> paperNames, QuestionsInterface questionsInterface)
    {
        this.context = context;
        this.paperNames = paperNames;
        this.questionsInterface = questionsInterface;
    }

    @Override
    public int getItemCount() {
        return paperNames.size();
    }

    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_question_papers_home,null);
        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionHolder holder, int position) {

        QuestionPaperHome paperHome = paperNames.get(position);

        if(paperHome.getStatus().equals("0"))
        {
           holder.txtSoon.setVisibility(View.VISIBLE);
        }
        else {
            holder.txtSoon.setVisibility(View.GONE);
        }

        holder.txtPaperName.setText(paperHome.getName());
        holder.txtQNo.setText(paperHome.getQuestionNo() + " questions");
        holder.txtQDate.setText(paperHome.getDate());

    }

    public class QuestionHolder extends RecyclerView.ViewHolder {

        TextView txtPaperName,txtQNo,txtQDate,txtSoon;

        public QuestionHolder(View itemView) {
            super(itemView);
            txtPaperName = itemView.findViewById(R.id.txtPaperName);
            txtQNo = itemView.findViewById(R.id.txtQNo);
            txtQDate = itemView.findViewById(R.id.txtQDate);
            txtSoon = itemView.findViewById(R.id.txtSoon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    QuestionPaperHome questionPaperHome = paperNames.get(getAdapterPosition());
                    if(questionPaperHome.getStatus().equals("1")) {

                        questionsInterface.questionTapped(getAdapterPosition());
                    }
                    else
                    {
                        Toast.makeText(context,"Comming soon",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
