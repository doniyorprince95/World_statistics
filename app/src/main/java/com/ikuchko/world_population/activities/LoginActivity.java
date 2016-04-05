package com.ikuchko.world_population.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;
import com.ikuchko.world_population.R;
import com.ikuchko.world_population.WorldPopulationApplication;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.submitButton) Button submitButton;
    @Bind(R.id.nameEditText) EditText nameEditText;
    @Bind(R.id.dateEditText) EditText dateEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        if (v == submitButton ) {
            Intent intent = new Intent(LoginActivity.this, HumanAgeActivity.class);
            intent.putExtra("userName", nameEditText.getText().toString());
            intent.putExtra("userDate", dateEditText.getText().toString());
            startActivity(intent);
        }
    }
}
