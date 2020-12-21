package com.alfonso.capstone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alfonso.capstone.R;

import java.util.List;

public class GenericAdapter<T> extends RecyclerView.Adapter<GenericAdapter.GenericAdapterViewHolder> {

    private List<T> list;
    private final IClickHandlerAdapter<T> listener;

    public GenericAdapter(IClickHandlerAdapter<T> listener) {
        this.listener = listener;
    }

    public void setList(List<T> routes) {
        list = routes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GenericAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutItem = R.layout.route_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutItem,parent,false);
        return new GenericAdapter.GenericAdapterViewHolder(view,listener, position -> list.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull GenericAdapterViewHolder holder, int position) {
        holder.route_name_tv.setText(list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    public static class GenericAdapterViewHolder<T> extends ViewHolderClick<T> {

        final TextView route_name_tv;

        public GenericAdapterViewHolder(@NonNull View itemView, @NonNull IClickHandlerAdapter<T> clickHandlerAdapter, @NonNull IModelPosition<T> position) {
            super(itemView, clickHandlerAdapter, position);
            route_name_tv = itemView.findViewById(R.id.item_name_route);
        }
    }
}
