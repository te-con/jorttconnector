package com.getehour.connector.jortt;

import org.junit.Test;

import static org.junit.Assert.*;

public class JorttCredentialsTest {
    @Test
    public void should_be_equal() {
        assertEquals(new JorttCredentials("a", "b"), new JorttCredentials("a", "b"));
    }

    @Test
    public void should_not_be_equal() {
        assertNotEquals(new JorttCredentials("a", "b"), new JorttCredentials("a", "c"));
    }
}