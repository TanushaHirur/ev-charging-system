package com.evcharging.system.service;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ChargingSessionMonitor {

    private final ExecutorService executorService =
            Executors.newSingleThreadExecutor();

    public void monitorSession(Long sessionId) {

        executorService.submit(() -> {

            System.out.println(
                    "Monitoring charging session " + sessionId
                            + " in background thread: "
                            + Thread.currentThread().getName()
            );

        });
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }
}