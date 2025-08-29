package com.zifo.ewb.wrapper.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class HealthTest {

    @InjectMocks
    private Health health;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(health, "host", "testHost");
    }

    @Test
    void testHealthCheck_ReturnsExpectedMessage() {
        String result = health.healthCheck();
        assertEquals("Usage Metrics API is up and running for testHost server", result);
    }
}
