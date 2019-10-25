package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;

public class ChallengeGameMainActivity extends BaseActivity {


    private RadioButton rbMale, rbFeMale;
    private TextView txtNext;
    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_game_main);


        Base.setStatusBarGradiant(this);
        setToolBar("Online Challenge");

        initViews();
        initControls();


    }

    private void initViews() {

        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFeMale = (RadioButton) findViewById(R.id.rbFeMale);
        txtNext = (TextView) findViewById(R.id.txtNext);
        edtName = (EditText) findViewById(R.id.edtName);

    }

    private void initControls() {

        rbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    rbFeMale.setChecked(false);
                    Session.setGender("male");
                }
            }
        });

        rbFeMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    rbMale.setChecked(false);
                    Session.setGender("female");
                }
            }
        });

        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtName.getText().toString().isEmpty()) {
                    Toast.makeText(ChallengeGameMainActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                } else if (!rbMale.isChecked() && !rbFeMale.isChecked()) {
                    Toast.makeText(ChallengeGameMainActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
                } else {
                    Session.setChallengeName(edtName.getText().toString());
                    startActivity(new Intent(ChallengeGameMainActivity.this, PlayerSelectionActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }
            }
        });

    }


}
