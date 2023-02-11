package com.google.mlkit.vision.demo.java.posedetector;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilTest {
    private final double delta = 0.1;

    @Test
    public void testGetDistance() {
        assertEquals("Testing getDistance 1", Util.getDistance(0.0, 0.0, 10.0, 0.0), 10.0, delta);
        assertEquals("Testing getDistance 2", Util.getDistance(0.0, 10.0, 0.0, 0.0), 10.0, delta);
        assertEquals("Testing getDistance 3", Util.getDistance(0.0, 0.0, 10.0, 10.0), 14.14, delta);
        assertEquals("Testing getDistance 4", Util.getDistance(10.0, 10.0, 0.0, 0.0), 14.14, delta);
        assertEquals("Testing getDistance 5", Util.getDistance(1.732, 1.0, 0.0, 0.0), 2.0, delta);
    }

    @Test
    public void testGetAngle() {
        assertEquals("Testing getAngle 1", Util.getAngle(0.0, 10.0, 0.0, 0.0, 10.0, 0.0), 90.0, delta);
        assertEquals("Testing getAngle 2", Util.getAngle(0.0, 10.0, 0.0, 0.0, 0.0, -10.0), 180.0, delta);
        assertEquals("Testing getAngle 3", Util.getAngle(10.0, 10.0, 0.0, 0.0, 10.0, 0.0), 45.0, delta);
        assertEquals("Testing getAngle 4", Util.getAngle(10.0, 10.0, 0.0, 0.0, 10.0, 0.0), 45.0, delta);
        assertEquals("Testing getAngle 5", Util.getAngle(1.0, 1.732, 0.0, 0.0, 1.0, 0.0), 60.0, delta);
    }
}