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
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-07-06.
 */

public class QuestionHomeAdapter extends RecyclerView.Adapter<QuestionHomeAdapter.QuestionHolder> {

    private ArrayList<QuestionsModel> paperNames;
    private Context context;
    private QuestionsInterface questionsInterface;

    public interface QuestionsInterface
    {
        void questtionTapped(int adapterPosition);
    }

    public
    QuestionHomeAdapter(QuestionsInterface questionsInterface, Context context, ArrayList<QuestionsModel> paperNames)
    {
        this.questionsInterface = questionsInterface;
        this.context = context;
        this.paperNames = paperNames;
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

        QuestionsModel questionsModel = paperNames.get(position);

        if(questionsModel.getStatus().equals("0"))
        {
           holder.txtSoon.setVisibility(View.VISIBLE);
        }
        else {
            holder.txtSoon.setVisibility(View.GONE);
        }

        holder.txtPaperName.setText(questionsModel.getqName());
        holder.txtQNo.setText(questionsModel.getqNumber() + " questions");
        holder.txtQDate.setText(questionsModel.getqDate());

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


                    QuestionsModel questionsModel = paperNames.get(getAdapterPosition());
                    if(questionsModel.getStatus().equals("1")) {

                        questionsInterface.questtionTapped(getAdapterPosition());
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
