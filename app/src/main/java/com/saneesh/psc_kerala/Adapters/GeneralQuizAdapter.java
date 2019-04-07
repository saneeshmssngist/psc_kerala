package com.saneesh.psc_kerala.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saneesh.psc_kerala.AppConstants;
import com.saneesh.psc_kerala.Model.GeneralModel;
import com.saneesh.psc_kerala.Model.GeneralTable;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saNeesH on 2018-06-04.
 */

public class GeneralQuizAdapter extends RecyclerView.Adapter<GeneralQuizAdapter.GeneralHolder> {

    private List<GeneralModel> generalDatas;
    private Context context;
    private int tokenNo;
    private boolean answerVisible;

    public GeneralQuizAdapter(Context context, List<GeneralModel> generalDatas, int tokenNo) {
        this.generalDatas = generalDatas;
        this.context = context;
        this.tokenNo = tokenNo;
        this.answerVisible = Session.getSharedBoolean(AppConstants.ANSSHOW);

    }

    @Override
    public int getItemCount() {
        return generalDatas.size();

    }

    @Override
    public GeneralHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.raw_general_quiz,null);
        return new GeneralHolder(view);
    }

    @Override
    public void onBindViewHolder(final GeneralHolder holder, int position) {

        GeneralModel generalTable = generalDatas.get(position);

        holder.txtViewQuestion.setText(generalTable.getQuestion());
        holder.txtViewAnswer.setText(generalTable.getAnswer());
        holder.txtViewNo.setText(String.valueOf((tokenNo*10)+position+1));

        if(answerVisible == false)
        {
            holder.txtViewHidden.setVisibility(View.VISIBLE);
            holder.txtViewAnswer.setVisibility(View.GONE);
        }
        else
        {
            holder.txtViewHidden.setVisibility(View.GONE);
            holder.txtViewAnswer.setVisibility(View.VISIBLE);
        }

        holder.txtViewHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    holder.txtViewHidden.setVisibility(View.GONE);
                    holder.txtViewAnswer.setVisibility(View.VISIBLE);
            }
        });

        holder.txtViewAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.txtViewHidden.setVisibility(View.VISIBLE);
                holder.txtViewAnswer.setVisibility(View.GONE);
            }
        });

    }

    public class GeneralHolder extends RecyclerView.ViewHolder {

        TextView txtViewQuestion,txtViewAnswer,txtViewNo,txtViewHidden;

        public GeneralHolder(View itemView) {
            super(itemView);

            txtViewQuestion = (TextView) itemView.findViewById(R.id.txtViewQuestion);
            txtViewAnswer = (TextView) itemView.findViewById(R.id.txtViewAnswer);
            txtViewNo = (TextView) itemView.findViewById(R.id.txtViewNo);
            txtViewHidden = (TextView) itemView.findViewById(R.id.txtViewHidden);

        }
    }

    public void resetData()
    {
        this.answerVisible = Session.getSharedBoolean(AppConstants.ANSSHOW);
        notifyDataSetChanged();

    }
}
