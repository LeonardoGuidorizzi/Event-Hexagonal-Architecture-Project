package dev.devdreamer.Event.infrastructure.configuration.bean;

import dev.devdreamer.Event.application.port.in.CreateEventUseCase;
import dev.devdreamer.Event.application.port.in.UpdateEventUseCase;
import dev.devdreamer.Event.application.usecases.*;
import dev.devdreamer.Event.application.port.out.EventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//todo tipo de configuração vem aqui dentro do infrastructure/ excessões também

@Configuration
public class UseCaseBeanConfig {
    @Bean
    public CreateEventUseCase createEventUseCase (EventRepository eventGateway){
        return new CreateEventUseCaseImpl(eventGateway);
    }

    @Bean
    public FindEventUseCase findEventUseCase(EventRepository eventGateway){
        return new FindEventUseCaseImpl(eventGateway);
    }

    @Bean
    public DeleteEventUseCase deleteEventUseCase(EventRepository eventGateway) {
        return new DeleteEventUseCaseImpl(eventGateway);
    }

    @Bean
    UpdateEventUseCase updateEventUseCase(EventRepository eventGateway){
        return new UpdateEventUseCaseImpl(eventGateway);
    }
}
