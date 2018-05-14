package com.practicals.chris.a2;

public class HighscoreRequestSQL {

    String datetime;
    int score;
    int level;


    HighscoreRequestSQL(String datetime, int score, int level) {
        this.datetime = datetime;
        this.score = score;
        this.level = level;
    }

    int getScore() {
        return score;
    }

    String getDate() {
        return datetime;
    }

    int getLevel() {
        return level;
    }
}
