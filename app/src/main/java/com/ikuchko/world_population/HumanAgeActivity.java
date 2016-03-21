package com.ikuchko.world_population;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HumanAgeActivity extends AppCompatActivity {
    @Bind(R.id.userNameTextView) TextView userName;
    @Bind(R.id.userDOBTextView) TextView userDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human_age);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        userName.setText(userName.getText() + intent.getStringExtra("userName"));
        userDOB.setText(userDOB.getText() + intent.getStringExtra("userDate"));
    }
}
