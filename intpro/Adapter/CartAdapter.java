package com.binary2quantum.android.intpro.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binary2quantum.android.intpro.R;
import com.binary2quantum.android.intpro.module.cartItem;

import java.util.ArrayList;

/**
 * Created by HP on 1/22/2019.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private Context mContext;
    private ArrayList<cartItem> mcartList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick_Delete(int position);

        void OnItemClick_Edit(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener =  listener;
    }


    public CartAdapter(Context context, ArrayList<cartItem> cartList) {
     this.mContext=context;
     this.mcartList=cartList;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(mContext).inflate(R.layout.cart_item,viewGroup,false);
        return new CartViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder cartViewHolder, final int i) {

        final cartItem item = mcartList.get(i);
        String product = item.getProduct();
        Log.e("cartadapter","product:"+product);

        String brand = item.getType();
        Log.e("cartadapter","band:"+brand);

        String quantity = item.getQuantity();
        Log.e("cartadapter","quantity:"+quantity);

        String price = item.getAmount();
        Log.e("cartadapter","price:"+price);

        String thickness = item.getAmount();
        Log.e("cartadapter","thickness:"+thickness);

        String size = item.getAmount();
        Log.e("cartadapter","size:"+size);


        cartViewHolder.t1.setText("Product : " + product);
        cartViewHolder.t2.setText("Brand : " + brand);
        cartViewHolder.t3.setText("Quantity : " + quantity);
        cartViewHolder.t4.setText("Price : " + "Rs." + price);
    }

    @Override
    public int getItemCount() {
        System.out.println("aaaacartsize"+ String.valueOf(mcartList.size()));
        return mcartList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t4;
        TextView t3;
//        EditText t3;
        ImageView img_delete,img_edit;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.ply_product);
            t2 = itemView.findViewById(R.id.ply_type);
            t3 = itemView.findViewById(R.id.ply_quantity);
            t4 = itemView.findViewById(R.id.ply_price);
            img_delete = itemView.findViewById(R.id.delete);
            img_edit  = itemView.findViewById(R.id.edit);

            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick_Delete(position);
                        }
                    }
                }
            });

            img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.OnItemClick_Edit(v,position);
                        }
                    }
                }
            });
        }
    }
}