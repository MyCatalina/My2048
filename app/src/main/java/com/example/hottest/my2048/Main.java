package com.example.hottest.my2048;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import view.GameView;

public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout gridRelated = (RelativeLayout) findViewById(R.id.rl_Main_gridrelated);
        GameView gameView = new GameView(this);
        gridRelated.addView(gameView);

    }
}
