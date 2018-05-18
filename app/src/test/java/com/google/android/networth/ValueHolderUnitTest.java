package com.google.android.networth;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ValueHolderUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void total_format(){

        // Setup
        MainActivity stubActivity = new MainActivity();
        ValueHolder testValuholder = new ValueHolder(stubActivity);

        // Given
        testValuholder.setTotal(2000);

        // Tests
        String expected = "2k Eur";
        assertEquals(expected,testValuholder.getTotal());
    }

    @Test
    public void yesterday_format(){
        // Setup
        MainActivity stubActivity = new MainActivity();
        ValueHolder testValuholder = new ValueHolder(stubActivity);

        // Given
        testValuholder.setTotal(2000);
        testValuholder.setYesterday(1000);
        testValuholder.calculateDelta();

        // Test
        String expected = "Yesterday: 1k Eur (100%)";
        assertEquals(expected,testValuholder.getYesterday());
    }

}


