package com.example.sda.retrofitapp.ui.calls;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sda.retrofitapp.R;
import com.example.sda.retrofitapp.model.CallActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sda on 29.05.17.
 */

public class CallsAdapter extends RecyclerView.Adapter<CallsAdapter.CallsHolder> {

    private List<CallActivity> activities = new ArrayList<>();

    public void setData(List<CallActivity> data) {
        activities.clear();
        activities.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public CallsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CallsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(CallsHolder holder, int position) {
        holder.setCall(activities.get(position));
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public class CallsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        public CallsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setCall(CallActivity call) {
            name.setText(call.getPhoneNumber());
        }
    }
}
