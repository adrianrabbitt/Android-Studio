package com.example.adrian.firebase;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Adrian on 15/04/2018.
 */
public class ConversationAdapterTest {


    @Test
    public void getFormattedDateFromTimestamp() throws Exception {

        String Expected = "Jan 18, 1970";
        long test = 1523737862;
        String Actual = ConversationAdapter.getFormattedDateFromTimestamp(1523737862);
        assertEquals(Expected, Actual);
        {
        }

    }
}
