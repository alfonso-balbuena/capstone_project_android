package com.alfonso.capstone.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alfonso.capstone.R;
import com.alfonso.capstone.model.Route;



import java.util.List;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RoutesAdapterViewHolder> {

    private List<Route> routeList;
    private final IClickHandlerAdapter<Route> listener;

    public RoutesAdapter(IClickHandlerAdapter<Route> listener) {
        this.listener = listener;
    }

    public void setRouteList(List<Route> routes) {
        routeList = routes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoutesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutItem = R.layout.route_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutItem,parent,false);
        return new RoutesAdapterViewHolder(view,listener,position -> routeList.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesAdapterViewHolder holder, int position) {
        holder.route_name_tv.setText(routeList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (routeList == null) ? 0 : routeList.size();
    }

    public static class RoutesAdapterViewHolder extends ViewHolderClick<Route> {

        final TextView route_name_tv;

        public RoutesAdapterViewHolder(@NonNull View itemView, @NonNull IClickHandlerAdapter<Route> clickHandlerAdapter, @NonNull IModelPosition<Route> position) {
            super(itemView, clickHandlerAdapter, position);
            route_name_tv = itemView.findViewById(R.id.item_name_route);
        }
    }
}
