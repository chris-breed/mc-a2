package com.practicals.chris.a2;

public class HighscoreRequestSQL {

    String datetime;
    int score;


    HighscoreRequestSQL(String datetime, String score) {
        this.datetime = datetime;
        this.score = Integer.parseInt(score);
    }

    int getScore(){
        return score;
    }

    String getDate(){
        return datetime;
    }
}
