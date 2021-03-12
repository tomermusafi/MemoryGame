package com.musafi.memorygame.game;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.musafi.memorygame.R;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private Card first;
    private Card second;
    private View tempView;
    private ArrayList<Card> cards;
    private int points;
    private int numberOfCards[];
    LinearLayout linearLayout;
    Context context;
    int[] images;
    int clickCounter;
    int level;
    TextView main_tv_level;
    public Game(){ }

    public Game(Context context, LinearLayout linearLayout, int numberOfCards[], int[] images, TextView main_tv_level){
        this.points = 0;
        this.clickCounter = 0;
        this.context = context;
        this.main_tv_level = main_tv_level;
        this.linearLayout = linearLayout;
        this.numberOfCards = numberOfCards;
        this.images = images;
        level = 0;
        this.main_tv_level.setText("Level "+ (level+1));
        cards = createCards();
        createBoard();
    }

    private void createBoard(){
        int numOfCols = (int) Math.sqrt(numberOfCards[level]/2);
        while (numberOfCards[level]%numOfCols!=0){
            numOfCols --;
        }
        int numOfRows = numberOfCards[level]/numOfCols;
        int buttonId = 0;
        for(int i = 0; i < numOfRows; i ++){
            LinearLayout linear = new LinearLayout(context);
            linear.setOrientation(LinearLayout.HORIZONTAL);
            linear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            for(int j = 0; j < numOfCols; j++){
                Button button = new Button(context);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
                param.setMargins(10,10,10,10);
                button.setLayoutParams(param);
                button.setId(buttonId);
                button.setPadding(5,5,5,5);
                button.setBackgroundResource(R.drawable.imgcard);
                buttonId++;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cardClicked(v);
                    }
                });
                linear.addView(button);
            }
            linearLayout.addView(linear);
        }
    }

    private ArrayList<Card> createCards(){
        ArrayList<Card> cards = new ArrayList<>();
        for(int i = 0; i < numberOfCards[level]/2; i ++){
            cards.add(new Card().setImage(images[i]).setEnable(true).setDiscovered(false));
            cards.add(new Card().setImage(images[i]).setEnable(true).setDiscovered(false));
        }
        Collections.shuffle(cards);
        for(int i = 0; i < numberOfCards[level]; i ++){
            cards.get(i).setId(i);
        }
        return cards;
    }

    private void cardClicked(View v){
        Card card = cards.get(v.getId());
        if(card.isEnable() && !card.isDiscovered() && clickCounter < 2){
            v.setBackgroundResource(cards.get(v.getId()).getImage());
            card.setEnable(false);
            clickCounter++;
            if(first == null){
                tempView = v;
            }
            switch (checkAMatch(card)){
                case 0:{
                    first = null;
                    second = null;
                    points += 2;
                    releaseCards();
                }
                break;
                case 1:
                    break;
                case -1:{
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tempView.setBackgroundResource(R.drawable.imgcard);
                            v.setBackgroundResource(R.drawable.imgcard);
                            first = null;
                            second = null;
                            releaseCards();
                        }
                    }, 500);

                }
                break;
            }
        }
        if(clickCounter >= 2){
            disableCards();
        }


        checkIfFoundAll();
    }

    private void checkIfFoundAll() {
        if (points == numberOfCards[level]){
            level++;
            if(level < numberOfCards.length)
                nextLevel();
            else{
                this.linearLayout.removeAllViews();
                this.main_tv_level.setText("");
                TextView textView = new TextView(context);
                Button button = new Button(context);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(600, 200);
                param.setMargins(0,20,0,0);
                button.setLayoutParams(param);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setText("Game Over");
                //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,100);
                textView.setTextColor(Color.parseColor("#ffffff"));
                button.setText("Play Again");
                button.setBackgroundColor(ContextCompat.getColor(context,R.color.blue_700));
                button.setTextColor(ContextCompat.getColor(context,R.color.white));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        level = 0;
                        nextLevel();
                    }
                });
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,55);
                linearLayout.addView(textView);
                linearLayout.addView(button);
            }
        }
    }
    private int checkAMatch(Card card) {
        if (first == null){
            first = card;
            return 1;
        }else if(second == null){
            second = card;
        }
        if(second != null){
            if(first.getImage() == second.getImage()){
                cards.get(first.getId()).setDiscovered(true);
                cards.get(second.getId()).setDiscovered(true);
                return 0;
            }
        }
        return -1;
    }
    private void releaseCards(){
        for(Card card : cards){
            if(!card.isDiscovered()){
                card.setEnable(true);
            }
        }
        clickCounter = 0;
    }
    private void disableCards(){
        for(Card card : cards){
            card.setEnable(false);
        }
    }

    private void nextLevel(){
        this.main_tv_level.setText("Level "+ (level+1));
        this.linearLayout.removeAllViews();
        this.points = 0;
        this.clickCounter = 0;
        cards = createCards();
        createBoard();
    }
}
