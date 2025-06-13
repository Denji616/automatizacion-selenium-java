package com.ejemplo.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AppTest {

    @Test
    public void testSuma() {
        int resultado = 2 + 2;
        Assert.assertEquals(resultado, 4, "2 + 2 should be 4");
    }
}