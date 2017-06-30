package com.example.sda.retrofitapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sda.retrofitapp.R;
import com.example.sda.retrofitapp.model.LoginResponse;
import com.example.sda.retrofitapp.network.ApiClient;
import com.example.sda.retrofitapp.utlis.PrefsManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // Bind views
    @BindView(R.id.email)
    EditText editMail;
    @BindView(R.id.password)
    EditText editPassword;

    private ApiClient apiClient;
    private PrefsManager prefsManager;

    // Set OnClickListener - provide id and your public method
    @OnClick(R.id.submit_button)
    public void onSubmitClick() {
        login(editMail.getText().toString(), editPassword.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        prefsManager = new PrefsManager(this);
        apiClient = new ApiClient(prefsManager);
    }

    private void login(String email, String password) {
        apiClient.getService().login(email, password)
        .enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    prefsManager.saveToken(response.body().getAccessToken());
                    Log.e("Access token", prefsManager.getToken());
                    openMainActivity();
                } else {
                    Log.e("Access token", "Login error");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_login_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
