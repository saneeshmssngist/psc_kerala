package com.saneesh.psc_kerala.Adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.R;

import java.util.List;

/**
 * Created by saNeesH on 2018-06-04.
 */

public class QuestionPaperAdapter extends RecyclerView.Adapter<QuestionPaperAdapter.QuestionHolder> {

    private List<QuestionsModel> generalDatas;
    private Context context;
    private int tokenNo;

    public QuestionPaperAdapter(Context context, List<QuestionsModel> generalDatas, int tokenNo) {
        this.generalDatas = generalDatas;
        this.context = context;
        this.tokenNo = tokenNo;
    }

    @Override
    public int getItemCount() {
        return generalDatas.size();
    }

    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_question_paper,null);
        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestionHolder holder, int position) {

        final QuestionsModel questionsModel = generalDatas.get(position);

        holder.txtQuestionNo.setText(String.valueOf(((tokenNo-1)*10)+position+1));
        holder.txtQuestion.setText(questionsModel.getQuestion());
        holder.txtOption1.setText(questionsModel.getOption1());
        holder.txtOption2.setText(questionsModel.getOption2());
        holder.txtOption3.setText(questionsModel.getOption3());
        holder.txtOption4.setText(questionsModel.getOption4());

        holder.imgAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(questionsModel.getAnswer().equals("1"))
                {
                    holder.layoutOption1.setBackgroundResource(R.drawable.question_paper_answer_bg);
                    holder.txtOption1.setTextColor(Color.parseColor("#ffffff"));
                }
                else if(questionsModel.getAnswer().equals("2"))
                {
                    holder.layoutOption2.setBackgroundResource(R.drawable.question_paper_answer_bg);
                    holder.txtOption2.setTextColor(Color.parseColor("#ffffff"));
                }
                else if(questionsModel.getAnswer().equals("3"))
                {
                    holder.layoutOption3.setBackgroundResource(R.drawable.question_paper_answer_bg);
                    holder.txtOption3.setTextColor(Color.parseColor("#ffffff"));
                }
                else
                {
                    holder.layoutOption4.setBackgroundResource(R.drawable.question_paper_answer_bg);
                    holder.txtOption4.setTextColor(Color.parseColor("#ffffff"));
                }

            }
        });


    }

    public class QuestionHolder extends RecyclerView.ViewHolder {

        TextView txtQuestionNo,txtQuestion,txtOption1,txtOption2,txtOption3,txtOption4;
        LinearLayout layoutOption1,layoutOption2,layoutOption3,layoutOption4;
        ImageView imgAnswer;

        public QuestionHolder(View itemView) {
            super(itemView);

            txtQuestionNo = itemView.findViewById(R.id.txtQuestionNo);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            txtOption1 = itemView.findViewById(R.id.txtOption1);
            txtOption2 = itemView.findViewById(R.id.txtOption2);
            txtOption3 = itemView.findViewById(R.id.txtOption3);
            txtOption4 = itemView.findViewById(R.id.txtOption4);
            imgAnswer = itemView.findViewById(R.id.imgAnswer);

            layoutOption1 = itemView.findViewById(R.id.layoutOption1);
            layoutOption2 = itemView.findViewById(R.id.layoutOption2);
            layoutOption3 = itemView.findViewById(R.id.layoutOption3);
            layoutOption4 = itemView.findViewById(R.id.layoutOption4);

        }
    }

}
