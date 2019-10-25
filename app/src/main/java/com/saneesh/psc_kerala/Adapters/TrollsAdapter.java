package com.saneesh.psc_kerala.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.saneesh.psc_kerala.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class TrollsAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<String> stringArrayList;

    public TrollsAdapter(@NonNull Context context, int resource, ArrayList<String> stringArrayList) {
        super(context, resource, stringArrayList);
        this.stringArrayList = stringArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.list_item_trolls, null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgView);
        final LinearLayout layoutProgress =  (LinearLayout) convertView.findViewById(R.id.layoutProgress);
        final AVLoadingIndicatorView avilayoutProgress  = (AVLoadingIndicatorView) convertView.findViewById(R.id.avilayoutProgress);

        layoutProgress.setVisibility(View.VISIBLE);
        avilayoutProgress.show();
        Glide
                .with(context)
                .load(stringArrayList.get(position))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        avilayoutProgress.smoothToHide();
                        layoutProgress.setVisibility(View.GONE);

                        return false;
                    }
                })
                .into(imageView);

        return convertView;
    }


}
