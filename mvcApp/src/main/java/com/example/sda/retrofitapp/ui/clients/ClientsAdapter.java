package com.example.sda.retrofitapp.ui.clients;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sda.retrofitapp.R;
import com.example.sda.retrofitapp.model.Client;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sda on 27.05.17.
 */

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ClientsHolder> {

    public ClientsAdapter(ClientClickListener listener) {
        this.listener = listener;
    }

    private List<Client> clients = new ArrayList<>();
    private ClientClickListener listener;

    public void setData(List<Client> clientList) {
        clients.clear();
        clients.addAll(clientList);
        notifyDataSetChanged();
    }

    @Override
    public ClientsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_adapter, parent, false);
        return new ClientsHolder(view);
    }

    @Override
    public void onBindViewHolder(ClientsHolder holder, int position) {
        holder.setClient(clients.get(position));
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    class ClientsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        private Client client;

        public ClientsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClientClick(client);
                }
            });
        }

        public void setClient(Client client) {
            this.client = client;
            name.setText(client.getName());
        }
    }

    public interface ClientClickListener {
        void onClientClick(Client client);
    }
}
