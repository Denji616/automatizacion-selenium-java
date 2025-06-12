package com.ejemplo.testng;

import org.junit.Test;
import static org.junit.Assert.assertEquals; 

public class AppTest {
    @Test
    public void testSuma() {
        int resultado = 2 + 2;
        assertEquals(4, resultado);
    }
}