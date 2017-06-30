package com.example.sda.retrofitapp.ui.contacts;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sda.retrofitapp.R;
import com.example.sda.retrofitapp.model.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by sda on 24.06.17.
 */

public class RealmContactAdapter extends RealmRecyclerViewAdapter<Contact, RealmContactAdapter.ContactHolder> {

    public RealmContactAdapter(@Nullable OrderedRealmCollection data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RealmContactAdapter.ContactHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        if (getData() != null) {
            holder.setupContact(getData().get(position));
        }
    }

    class ContactHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        public ContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setupContact(Contact contact) {
            name.setText(contact.getName());
        }
    }
}
