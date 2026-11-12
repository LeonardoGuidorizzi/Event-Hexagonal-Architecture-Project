package dev.devdreamer.Event.infrastructure.adapter.out.configuration;

import dev.devdreamer.Event.application.port.in.ListEventsUseCase;
import dev.devdreamer.Event.application.port.in.DeleteExpiredCancelledEventsUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class UseCaseConfig {

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    DeleteExpiredCancelledEventsUseCase deleteExpiredCancelledEventsUseCase(
            EventRepository repository,
            Clock clock
    ) {
        return new DeleteExpiredCancelledEventsImpl(repository, clock);
    }

    @Bean
    CreateEventUseCaseImpl createEventUseCase(EventRepository repository) {
        return new CreateEventUseCaseImpl(repository);
    }

    @Bean
    ListEventsUseCase listEventsUseCase(EventRepository repository) {
        return new ListEventsUseCaseImpl(repository);
    }


    @Bean
    PublishEventUseCaseImpl publishEventUseCase(EventRepository repository) {
        return new PublishEventUseCaseImpl(repository);
    }

    @Bean
    CancelEventUseCaseImpl cancelEventUseCase(EventRepository repository) {
        return new CancelEventUseCaseImpl(repository);
    }
}
