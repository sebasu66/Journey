package com.sebasu.journey.util;

import java.util.Random;

public class ValueRange {
    final int min;
    final int max;
    int current;

    public ValueRange(int min, int max, int start) {
        this.min = min;
        this.max = max;
        this.current = start;
    }

    //increase current by 1 cant be more than max
    public void increase() {
        if (current < max) {
            current++;
        }
    }

    //decrease current by 1 cant be less than min
    public void decrease() {
        if (current > min) {
            current--;
        }
    }

    public int getCurrent() {
        return current;
    }

    public int getPriorityValue() {
        //randomly generate a value between min and max
        Random random = new Random();
        int diff = (max - min) / 2;
        return Math.min(max, random.nextInt(max - diff) + diff + min);
    }

    public int getNoPriorityValue() {
        return min;
    }

    public void modifyCurrent(int modifier) {
        if (current + modifier > max) {
            current = max;
        } else if (current + modifier < min) {
            current = min;
        } else {
            current += modifier;
        }
    }
}
