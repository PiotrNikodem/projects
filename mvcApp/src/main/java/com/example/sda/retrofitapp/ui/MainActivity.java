package com.example.sda.retrofitapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sda.retrofitapp.R;
import com.example.sda.retrofitapp.model.CallActivity;
import com.example.sda.retrofitapp.model.Client;
import com.example.sda.retrofitapp.model.Contact;
import com.example.sda.retrofitapp.network.ApiClient;
import com.example.sda.retrofitapp.ui.calls.CallsAdapter;
import com.example.sda.retrofitapp.ui.clients.ClientDetailsActivity;
import com.example.sda.retrofitapp.ui.clients.ClientsAdapter;
import com.example.sda.retrofitapp.ui.contacts.RealmContactAdapter;
import com.example.sda.retrofitapp.utlis.PrefsManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sda on 25.05.17.
 */

public class MainActivity extends AppCompatActivity implements ClientsAdapter.ClientClickListener {

    private PrefsManager prefsManager;
    private ApiClient apiClient;
    private ClientsAdapter clientsAdapter;
    private CallsAdapter callsAdapter;
    private Realm realm;
    private RealmContactAdapter contactAdapter;
    private RealmResults<Contact> contactRealmResults;

    @BindView(R.id.clients_recycler)
    RecyclerView recycler;
    @BindView(R.id.clients_progress_bar)
    ProgressBar progressBar;

    @OnClick(R.id.getCalls)
    public void downloadCalls() {
        getCalls();
    }

    @OnClick(R.id.getClients)
    public void downloadClients() {
        getClients();
    }

    @OnClick(R.id.getContacts)
    public void downloadContacts() {
        getContacts();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clients, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_clients_add) {
            openDetailsView(null);
            return true;
        }
        return false;
    }

    private void init() {
        realm = Realm.getDefaultInstance();
        prefsManager = new PrefsManager(this);
        apiClient = new ApiClient(prefsManager);
        clientsAdapter = new ClientsAdapter(this);
        callsAdapter = new CallsAdapter();

        contactRealmResults = realm.where(Contact.class).findAll();
        contactAdapter = new RealmContactAdapter(contactRealmResults, true);

        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        Log.e("STATE", "OnDestroy");
        realm.close();
        super.onDestroy();
    }

    @Override
    public void onClientClick(Client client) {
        Toast.makeText(this, client.getName(), Toast.LENGTH_SHORT).show();
        openDetailsView(client);
    }

    private void openDetailsView(@Nullable Client client) {
        Intent intent = new Intent(this, ClientDetailsActivity.class);
        intent.putExtra(getString(R.string.extra_client), client);
        startActivity(intent);
    }

    private void getCalls() {
        showProgress();
        recycler.setAdapter(callsAdapter);
        apiClient.getService().getActivities()
                .enqueue(new Callback<List<CallActivity>>() {
                    @Override
                    public void onResponse(Call<List<CallActivity>> call, Response<List<CallActivity>> response) {
                        if (response.isSuccessful()) {
                            callsAdapter.setData(response.body());
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
                        }
                        hideProgress();
                    }

                    @Override
                    public void onFailure(Call<List<CallActivity>> call, Throwable t) {
                        // Need to handle the error
                        hideProgress();
                        showErrorMessage();
                    }
                });
    }

    private void getClients() {
        showProgress();
        recycler.setAdapter(clientsAdapter);
        apiClient.getService().getClients().enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if (response.isSuccessful()) {
                    clientsAdapter.setData(response.body());
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                hideProgress();
                showErrorMessage();
            }
        });
    }

    private void getContacts() {
        showProgress();
        recycler.setAdapter(contactAdapter);
        apiClient.getService().getContacts().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, final Response<List<Contact>> response) {
                if (response.isSuccessful()) {
                    // save them to the database
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            for (Contact contact : response.body()) {
                                realm.copyToRealmOrUpdate(contact);
                            }
                        }
                    });
                    contactAdapter.updateData(contactRealmResults);
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                hideProgress();
                showErrorMessage();
            }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(this, getString(R.string.toast_login_error), Toast.LENGTH_SHORT).show();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.INVISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
    }
}
