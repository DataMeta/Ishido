package edu.ramapo.dmelniko.ishido;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartupActivity extends AppCompatActivity {
    Boolean isNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        Button newGame = (Button)findViewById(R.id.new_game_button);
        Button loadGame = (Button)findViewById(R.id.load_game_button);


        newGame.setOnClickListener(new View.OnClickListener()
        {   @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(StartupActivity.this, MainActivity.class);
                myIntent.putExtra("isNewGame", true);
                startActivity(myIntent);
                finish();
            }
        });
        loadGame.setOnClickListener(new View.OnClickListener()
        {   @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(StartupActivity.this, MainActivity.class);
                myIntent.putExtra("isNewGame", false);
                startActivity(myIntent);
                finish();
            }
        });
    }

}
