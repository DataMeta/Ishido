package edu.ramapo.dmelniko.ishido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TurnOrderActivity extends Activity
{
    Spinner headsOrTails;
    Button flipCoin;
    Button startGame;
    TextView tossResult;
    String firstPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_order);

        flipCoin = (Button)findViewById(R.id.flip_coin_button);
        startGame = (Button)findViewById(R.id.start_game_button);
        tossResult = (TextView)findViewById(R.id.toss_result);
        startGame.setVisibility(View.INVISIBLE);

        // Spinner to select different search algorithms
        headsOrTails = (Spinner) findViewById(edu.ramapo.dmelniko.ishido.R.id.coin_spinner);

        final List<String> searchList = new ArrayList<>();
        searchList.add("HEADS");
        searchList.add("TAILS");

        // Create ArrayAdapters using the string arrays and a default spinner layout
        ArrayAdapter<String> searchDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,searchList);

        // Specify the layout to use when the list of choices appears
        searchDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapters to the spinners
        headsOrTails.setPrompt("CALL IT");
        headsOrTails.setAdapter(searchDataAdapter);

        flipCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random ran = new Random();
                int coin = ran.nextInt(2);
                int call = headsOrTails.getSelectedItemPosition();

                if (coin == 0) {
                    tossResult.setText("RESULT: HEADS");
                } else {
                    tossResult.setText("RESULT: TAILS");
                }

                if (coin == call) {
                    firstPlayer = "HUMAN";
                    Toast.makeText(TurnOrderActivity.this, "HUMAN GOES FIRST", Toast.LENGTH_LONG).show();
                } else {
                    firstPlayer = "COMPUTER";
                    Toast.makeText(TurnOrderActivity.this, "COMPUTER GOES FIRST", Toast.LENGTH_LONG).show();
                }
                startGame.setVisibility(View.VISIBLE);
            }
        });
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(TurnOrderActivity.this, MainActivity.class);

                myIntent.putExtra("firstPlayer", firstPlayer);
                myIntent.putExtra("isNewGame", true);
                startActivity(myIntent);
                finish();
            }
        });
    }

}
