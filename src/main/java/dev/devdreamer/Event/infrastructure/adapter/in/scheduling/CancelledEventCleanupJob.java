package dev.devdreamer.Event.infrastructure.adapter.in.scheduling;

import dev.devdreamer.Event.application.port.in.DeleteExpiredCancelledEventsUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CancelledEventCleanupJob {

    private final DeleteExpiredCancelledEventsUseCase cleanupUseCase;

    public CancelledEventCleanupJob(DeleteExpiredCancelledEventsUseCase cleanupUseCase) {
        this.cleanupUseCase = cleanupUseCase;
    }

    @Scheduled(fixedDelayString = "${events.cleanup.delay:30000}")
    public void deleteExpiredCancelledEvents() {
        cleanupUseCase.execute();
    }
}
