package dev.devdreamer.Event.infrastructure.bean;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.gateway.EventGateway;
import dev.devdreamer.Event.core.usecases.CreateEventUseCase;
import dev.devdreamer.Event.core.usecases.CreateEventUseCaseImpl;
import dev.devdreamer.Event.core.usecases.FindEventUseCase;
import dev.devdreamer.Event.core.usecases.FindEventUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UseCaseBeanConfig {
    @Bean
    public CreateEventUseCase createEventUseCase (EventGateway eventGateway){
        return new CreateEventUseCaseImpl(eventGateway);
    }
    @Bean
    public FindEventUseCase findEventUseCase(EventGateway eventGateway){
        return new FindEventUseCaseImpl(eventGateway);
    }
}
