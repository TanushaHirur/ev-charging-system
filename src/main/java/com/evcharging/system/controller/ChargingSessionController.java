package com.evcharging.system.controller;

import com.evcharging.system.entity.ChargingSession;
import com.evcharging.system.service.ChargingSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class ChargingSessionController {

    private final ChargingSessionService chargingSessionService;

    public ChargingSessionController(ChargingSessionService chargingSessionService) {
        this.chargingSessionService = chargingSessionService;
    }

    @PostMapping
    public ResponseEntity<ChargingSession> createSession(
            @RequestBody ChargingSession session) {

        return ResponseEntity.ok(
                chargingSessionService.createSession(session)
        );
    }

    @GetMapping
    public ResponseEntity<List<ChargingSession>> getAllSessions() {

        return ResponseEntity.ok(
                chargingSessionService.getAllSessions()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingSession> getSessionById(
            @PathVariable Long id) {

        return chargingSessionService.getSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<ChargingSession> startSession(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                chargingSessionService.startSession(id)
        );
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<ChargingSession> completeSession(
            @PathVariable Long id,
            @RequestParam double energyConsumed) {

        return ResponseEntity.ok(
                chargingSessionService.completeSession(
                        id,
                        energyConsumed
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargingSession> updateSession(
            @PathVariable Long id,
            @RequestBody ChargingSession updatedSession) {

        return ResponseEntity.ok(
                chargingSessionService.updateSession(
                        id,
                        updatedSession
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(
            @PathVariable Long id) {

        chargingSessionService.deleteSession(id);

        return ResponseEntity.noContent().build();
    }
}