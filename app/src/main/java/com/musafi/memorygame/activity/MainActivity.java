package com.musafi.memorygame.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.musafi.memorygame.game.Game;
import com.musafi.memorygame.R;


public class MainActivity extends AppCompatActivity {

   private LinearLayout main_linear_board;
   private TextView main_tv_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        main_linear_board = findViewById(R.id.main_linear_board);
        main_tv_level = findViewById(R.id.main_tv_level);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Game(MainActivity.this, main_linear_board, new int[]{8,18,32},
                        new int[] {
                                R.drawable.img1,
                                R.drawable.img2,
                                R.drawable.img3,
                                R.drawable.img4
                                ,
                                R.drawable.img5,
                                R.drawable.img6,
                                R.drawable.img7
                                ,
                                R.drawable.img8,
                                R.drawable.img9,
                                R.drawable.img10
                                ,
                                R.drawable.img11,
                                R.drawable.img12,
                                R.drawable.img13
                                ,
                                R.drawable.img14,
                                R.drawable.img15,
                                R.drawable.img16

                }, main_tv_level);
            }
        });

    }

}