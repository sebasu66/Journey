package com.sebasu.journey;

import com.sebasu.journey.util.ValueRange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueRangeTest {

    ValueRange range;
    @BeforeEach
    void setUp() {
        range = new ValueRange(1, 10,5);
    }

    @AfterEach
    void tearDown() {
        range = null;
    }

    @Test
    void getPriorityValue() {
        for (int i = 0; i < 1000; i++) {
            assertTrue(range.getPriorityValue() >= 5 && range.getPriorityValue() <= 10);
        }
    }

    //test a value range with no priority value to be less than max on average
    @Test
    void getNoPriorityValue() {
        for (int i = 0; i < 1000; i++) {
            assertTrue(range.getNoPriorityValue() < 10);
        }
    }




}