package dev.devdreamer.Event.infrastructure.configuration.bean;

import dev.devdreamer.Event.application.usecases.*;
import dev.devdreamer.Event.core.gateway.EventGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//todo tipo de configuração vem aqui dentro do infrastructure/ excessões também

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

    @Bean
    public DeleteEventUseCase deleteEventUseCase(EventGateway eventGateway) {
        return new DeleteEventUseCaseImpl(eventGateway);
    }

    @Bean
    UpdateEventUseCase updateEventUseCase(EventGateway eventGateway){
        return new UpdateEventUseCaseImpl(eventGateway);
    }
}
