package com.saneesh.psc_kerala.Activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerSelectionActivity extends AppCompatActivity {


    private CircleImageView imgViewOpponent,imgViewPlayer;
    private TextView txtViewOpponent, txtPlayerCount;
    private LottieAnimationView lottieAnim;
    private int flag = 1;
    private BroadcastReceiver broadcastReceiver;
    static boolean activityActive = false;
    private Handler searchHandler = new Handler();
    private MaterialButton btmNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_selection);

        imgViewOpponent = (CircleImageView) findViewById(R.id.imgViewOpponent);
        imgViewPlayer = (CircleImageView) findViewById(R.id.imgViewPlayer);
        txtViewOpponent = (TextView) findViewById(R.id.txtViewOpponent);
        lottieAnim = (LottieAnimationView) findViewById(R.id.lottieAnim);

        txtPlayerCount = (TextView) findViewById(R.id.txtPlayerCount);
        btmNext = (MaterialButton) findViewById(R.id.btmNext);

        txtPlayerCount.setText(String.format("%d Online Players", selectRandomTotalUsers()));

        switch (Session.getGender()) {
            case "male":
                imgViewPlayer.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.male));
                break;
            case "female":
                imgViewPlayer.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.female));
                break;
        }

        btmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlayerSelectionActivity.this, ChallengeScreenActivity.class)
                        .putExtra("name", txtViewOpponent.getText().toString())
                        .putExtra("gender", String.valueOf(flag))
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        searchOpponent();

    }

    public void searchOpponent() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                imgViewOpponent.setVisibility(View.VISIBLE);
                txtViewOpponent.setVisibility(View.VISIBLE);
                lottieAnim.setVisibility(View.GONE);
                txtViewOpponent.setText("");

                flag = new Random().nextInt(2) + 1;
                String[] names;

                switch (flag) {
                    case 1:
                        names = getResources().getStringArray(R.array.opponent_names_male);
                        break;
                    case 2:
                        names = getResources().getStringArray(R.array.opponent_names_female);
                        break;
                    default:
                        names = getResources().getStringArray(R.array.opponent_names_male);
                }

                txtViewOpponent.setText(names[new Random().nextInt(names.length)]);

                switch (String.valueOf(flag)) {
                    case "1":
                        imgViewOpponent.setImageDrawable(ContextCompat.getDrawable(PlayerSelectionActivity.this, R.drawable.male));
                        break;
                    case "2":
                        imgViewOpponent.setImageDrawable(ContextCompat.getDrawable(PlayerSelectionActivity.this, R.drawable.female));
                        break;
                }

                new Handler().postDelayed(runnable, 3000);

            }
        }, 5000);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            btmNext.setVisibility(View.VISIBLE);
        }
    };

    public int selectRandomTotalUsers() {
        return new Random().nextInt(301) + 200;
    }


}
