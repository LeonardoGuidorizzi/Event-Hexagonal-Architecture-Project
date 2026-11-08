package dev.devdreamer.Event.infrastructure.config;

import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.application.usecases.CancelEventUseCaseImpl;
import dev.devdreamer.Event.application.usecases.CreateEventUseCaseImpl;
import dev.devdreamer.Event.application.usecases.PublishEventUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    CreateEventUseCaseImpl createEventUseCase(EventRepository repository) {
        return new CreateEventUseCaseImpl(repository);
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
