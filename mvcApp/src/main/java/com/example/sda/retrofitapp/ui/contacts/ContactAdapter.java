package com.example.sda.retrofitapp.ui.contacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sda.retrofitapp.R;
import com.example.sda.retrofitapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sda on 24.06.17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private List<Contact> contactList = new ArrayList<>();

    public void setData(List<Contact> contacts) {
        contactList.clear();
        contactList.addAll(contacts);
        notifyDataSetChanged();
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        holder.setupContact(contactList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
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
