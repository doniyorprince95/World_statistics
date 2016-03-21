package com.ikuchko.world_population;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
