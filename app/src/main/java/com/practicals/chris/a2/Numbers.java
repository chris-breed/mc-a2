package com.practicals.chris.a2;

import java.io.Serializable;

class Numbers implements Serializable {

    private final int goalNumber;
    private final int[] numberArray;

    Numbers(int[] numbs, int goal) {


        numberArray = numbs;

        goalNumber = goal;
    }

    int[] getNumberArray() {
        return numberArray;
    }

    int getGoalNumber() {
        return goalNumber;
    }
}
