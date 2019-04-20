package com.binary2quantum.android.intpro.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binary2quantum.android.intpro.R;
import com.binary2quantum.android.intpro.module.Pricemodule;

import java.util.ArrayList;

/**
 * Created by HP on 1/21/2019.
 */

public class PlywooddetailsAdapter extends RecyclerView.Adapter<PlywooddetailsAdapter.PlywoodViewHolder> {

    private Context mContext;
    private ArrayList<Pricemodule> mExampleList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener =  listener;
    }


    public PlywooddetailsAdapter(Context context, ArrayList<Pricemodule> exampleList)
    {
        this.mContext = context;
        this.mExampleList = exampleList;
    }
    @NonNull
    @Override
    public PlywooddetailsAdapter.PlywoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.size_list,viewGroup,false);

        return new PlywoodViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PlywooddetailsAdapter.PlywoodViewHolder plywoodViewHolder, int i) {

        Pricemodule item = mExampleList.get(i);
        String thickness = item.getUthickness();
        String size = item.getUsize();
        String price = item.getUprice();

        plywoodViewHolder.tthickness.setText("Thickness :"+" "+thickness);
        plywoodViewHolder.tsize.setText("Size :"+" "+size);
        plywoodViewHolder.tprice.setText(price);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class PlywoodViewHolder extends RecyclerView.ViewHolder {
        public TextView tthickness;
        public TextView tsize;
        public TextView tprice;

        public PlywoodViewHolder(@NonNull final View itemView) {
            super(itemView);

            tthickness=(TextView)itemView.findViewById(R.id.thicknees1);
            tsize=(TextView)itemView.findViewById(R.id.size1);
            tprice=(TextView)itemView.findViewById(R.id.price1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null)
                    {
                        int position=getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
