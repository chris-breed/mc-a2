package com.practicals.chris.a2;

import java.util.ArrayList;
import java.util.Random;

class Countdown {

    private int max;
    private int min;
    private Random rand;

    private int goalNumber;


    private ArrayList<Integer> baseNumbers;

    // Constructor
    Countdown(int max, int min) {
        this.max = max;
        this.min = min;

        this.rand = new Random();
    }


    void start() {
        baseNumbers = new ArrayList<>();
        goalNumber = rand.nextInt(max - min) + max; // Generates a random goal number between min and max values.
    }

    void addToArray(int generatedNumber) {
        baseNumbers.add(generatedNumber);
    }

    int getGoalNumber() {
        return goalNumber;
    }
}