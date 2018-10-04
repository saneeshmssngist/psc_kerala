package com.saneesh.psc_kerala.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saneesh.psc_kerala.Activities.GeneralActivity;
import com.saneesh.psc_kerala.Interfaces.GeneralQuizInterface;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.R;

/**
 * Created by saNeesH on 2018-06-07.
 */

public class GeneralQuizPagerDataAdapter extends RecyclerView.Adapter<GeneralQuizPagerDataAdapter.PagerHolder> {

    private Context context;
    private int tokenCount;
    private GeneralQuizInterface generalQuizInterface;
    private int selectedItem = 1;

    public GeneralQuizPagerDataAdapter(GeneralQuizInterface generalQuizInterface, Context context, int tokenCount) {
        this.generalQuizInterface = generalQuizInterface;
        this.tokenCount = tokenCount;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return tokenCount;
    }

    @Override
    public PagerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_pager_data,null);

        return new PagerHolder(view);
    }

    @Override
    public void onBindViewHolder(PagerHolder holder, final int position) {

        if((position+1) == selectedItem)
        {
            holder.txtViewTokenNo.setTextColor(Color.parseColor("#9e026a"));
            holder.txtViewTokenNo.setTextSize(16);

        }
        else {

            holder.txtViewTokenNo.setTextColor(Color.parseColor("#ffffff"));
        }
        holder.txtViewTokenNo.setText(String.valueOf(position + 1));

        holder.txtViewTokenNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                generalQuizInterface.OnTokenButtonTapped(position);

            }
        });

    }

    public class PagerHolder extends RecyclerView.ViewHolder {

        TextView txtViewTokenNo;

        public PagerHolder(View itemView) {
            super(itemView);

            txtViewTokenNo = (TextView) itemView.findViewById(R.id.txtViewTokenNo);
        }
    }

    public void reloadPager(int itemNo)
    {
        selectedItem = itemNo;
        notifyDataSetChanged();
    }
}
