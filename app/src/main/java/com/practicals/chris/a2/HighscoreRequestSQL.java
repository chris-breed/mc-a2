package com.practicals.chris.a2;

class HighscoreRequestSQL {

    private final String datetime;
    private final int score;
    private final int level;


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
