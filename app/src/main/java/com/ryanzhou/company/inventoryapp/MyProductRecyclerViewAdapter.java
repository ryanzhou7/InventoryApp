package com.ryanzhou.company.inventoryapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ryanzhou.company.inventoryapp.model.Product;

import java.util.List;

public class MyProductRecyclerViewAdapter extends RecyclerView.Adapter<MyProductRecyclerViewAdapter.ViewHolder> {

    private final List<Product> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyProductRecyclerViewAdapter(List<Product> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = getmValues().get(position);
        holder.textViewPrice.setText(holder.mItem.getPriceForDisplay());
        holder.textViewName.setText(holder.mItem.getName());
        holder.textViewQuantity.setText(String.valueOf(holder.mItem.getQuantity()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onClickProduct(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getmValues().size();
    }

    public List<Product> getmValues() {
        return mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textViewName;
        public final TextView textViewPrice;
        public final TextView textViewQuantity;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            textViewName = (TextView) view.findViewById(R.id.textViewNameItem);
            textViewPrice = (TextView) view.findViewById(R.id.textViewPriceItem);
            textViewQuantity = (TextView) view.findViewById(R.id.textViewQuantityItem);
        }

    }

    public interface OnListFragmentInteractionListener {
        void onClickProduct(Product item);
    }

}
