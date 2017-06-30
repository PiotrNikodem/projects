package com.example.sda.retrofitapp.ui.clients;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sda.retrofitapp.R;
import com.example.sda.retrofitapp.model.Client;
import com.example.sda.retrofitapp.network.ApiClient;
import com.example.sda.retrofitapp.utlis.PrefsManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sda on 29.05.17.
 */

public class ClientDetailsActivity extends AppCompatActivity {

    @BindView(R.id.details_name)
    EditText et_name;
    @BindView(R.id.details_city)
    EditText et_city;
    @BindView(R.id.details_country)
    EditText et_country;
    @BindView(R.id.details_phone)
    EditText et_phone;

    @OnClick(R.id.details_save)
    public void onSaveClick() {
        if (client == null) {
            saveNewClient();
        } else updateClient();
    }

    private Client client;
    private ApiClient apiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        apiClient = new ApiClient(new PrefsManager(this));
        // Get Client from intent
        client = (Client) getIntent().getSerializableExtra(getString(R.string.extra_client));
        if (client != null) {
            // condition ? value if true : value if false
            et_name.setText(client.getName() != null ? client.getName() : "");
            et_city.setText(client.getCity());
            et_country.setText(client.getCountry());
            et_phone.setText(client.getPhone());
        }
    }

    private void saveNewClient() {
        setClientDetails();

        List<Client> clientList = new ArrayList<>();
        clientList.add(client);
        apiClient.getService().createClient(clientList)
                .enqueue(new Callback<List<Client>>() {
                    @Override
                    public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                        if (response.isSuccessful()) {
                            showSuccessMessage();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Client>> call, Throwable t) {
                        showErrorMessage();
                    }
                });
    }

    private void updateClient() {
        setClientDetails();

        apiClient.getService().updateClient(client)
                .enqueue(new Callback<Client>() {
                    @Override
                    public void onResponse(Call<Client> call, Response<Client> response) {
                        if (response.isSuccessful()) {
                            showSuccessMessage();
                        }
                    }

                    @Override
                    public void onFailure(Call<Client> call, Throwable t) {
                        showErrorMessage();
                    }
                });
    }

    private void showErrorMessage() {
        Toast.makeText(this, getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
    }

    private void showSuccessMessage() {
        Toast.makeText(this, getString(R.string.toast_update_success), Toast.LENGTH_SHORT).show();
    }

    private void setClientDetails() {
        if (client == null) {
            client = new Client();
        }
        // Assign input values to client object
        client.setName(et_name.getText().toString());
        client.setCity(et_city.getText().toString());
        client.setCountry(et_country.getText().toString());
        client.setPhone(et_phone.getText().toString());
    }
}
