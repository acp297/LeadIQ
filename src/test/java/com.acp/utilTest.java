package com.acp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class utilTest {
    private Util util;

    @Before
    public void setUp(){
       util = new Util();
    }

    @Test
    public void buildResponseAsJsonTest(){
        String expectedMessage = "{\"message\":\"This is a error message\"}";
        String actualMessage = util.buildResponseAsJson("This is a error message");

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void buildResponseAsJsonWithNullMessageShouldNotThrowException(){
        util.buildResponseAsJson(null);

    }

}


