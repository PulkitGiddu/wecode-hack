package com.wecode.bookit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration to enable Spring Scheduling
 * Enables @Scheduled annotations for cron jobs
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
}

