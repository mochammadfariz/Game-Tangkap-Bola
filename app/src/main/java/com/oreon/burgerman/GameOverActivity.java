package com.oreon.burgerman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity
{
    private Button cobaLagi;
    private TextView displayskor;
    private String skor;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_over);

//        menerima skor(put extra) dari burgerManView.java
        skor=getIntent().getExtras().get("score").toString();

        cobaLagi = (Button) findViewById(R.id.tryagain_btn);
        displayskor = (TextView) findViewById(R.id.displayskor);

        cobaLagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });

        displayskor.setText("Skor akhir:"+  skor);

    }
}
