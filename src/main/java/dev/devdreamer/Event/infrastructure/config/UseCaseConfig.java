package dev.devdreamer.Event.infrastructure.config;

import dev.devdreamer.Event.application.port.in.FindPublishedEventsUseCase;
import dev.devdreamer.Event.application.port.in.ListEventsUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    CreateEventUseCaseImpl createEventUseCase(EventRepository repository) {
        return new CreateEventUseCaseImpl(repository);
    }

    @Bean
    ListEventsUseCase listEventsUseCase(EventRepository repository) {
        return new ListEventsUseCaseImpl(repository);
    }
    @Bean
    FindPublishedEventsUseCase findPublishedEventsUseCase (EventRepository repository){
        return new FindPublishedEventsUseCaseImpl(repository);
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
