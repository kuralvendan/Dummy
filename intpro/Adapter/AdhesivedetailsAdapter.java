package com.binary2quantumtechbase.andapp.intpro.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binary2quantumtechbase.andapp.intpro.R;
import com.binary2quantumtechbase.andapp.intpro.module.Pricemodule;

import java.util.ArrayList;

public class AdhesivedetailsAdapter extends RecyclerView.Adapter<AdhesivedetailsAdapter.AdhesiveViewHolder> {

    private Context mContext;
    private ArrayList<Pricemodule> mExampleList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener =  listener;
    }

    public AdhesivedetailsAdapter(Context context, ArrayList<Pricemodule> exampleList) {
        this.mContext = context;
        this.mExampleList = exampleList;
    }



    @NonNull
    @Override
    public AdhesiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.size_list,viewGroup,false);
        return new AdhesiveViewHolder(v);

//        return new PlywooddetailsAdapter.PlywoodViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull AdhesiveViewHolder adhesiveViewHolder, int i) {
        Pricemodule item = mExampleList.get(i);
        String thickness = item.getUthickness();
        String size = item.getUsize();
        String price = item.getUprice();

        adhesiveViewHolder.tthickness.setText("Type :"+" "+thickness);
        adhesiveViewHolder.tsize.setText("Weight :"+" "+size);
        adhesiveViewHolder.tprice.setText(price);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class AdhesiveViewHolder extends RecyclerView.ViewHolder {
        public TextView tthickness;
        public TextView tsize;
        public TextView tprice;

        public AdhesiveViewHolder(@NonNull View itemView) {
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
//    extends RecyclerView.Adapter<PlywooddetailsAdapter.PlywoodViewHolder>
}
