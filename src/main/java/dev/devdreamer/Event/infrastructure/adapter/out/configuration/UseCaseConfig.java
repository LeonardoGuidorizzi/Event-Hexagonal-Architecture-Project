package dev.devdreamer.Event.infrastructure.adapter.out.configuration;

import dev.devdreamer.Event.application.port.in.DeleteExpiredCancelledEventsUseCase;
import dev.devdreamer.Event.application.port.in.*;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.application.port.out.*;
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

    @Bean
    DeleteEventUseCaseImpl deleteEventUseCase(EventRepository repository) {
        return new DeleteEventUseCaseImpl(repository);
    }

    @Bean
    UpdateEventUseCaseImpl updateEventUseCase(EventRepository repository) {
        return new UpdateEventUseCaseImpl(repository);
    }

    @Bean
    SignUpUseCase signUpUseCase(UserRepository users, AccountRepository accounts,
                                PasswordHasher passwordHasher, AccessTokenIssuer tokenIssuer) {
        return new SignUpUseCaseImpl(users, accounts, passwordHasher, tokenIssuer);
    }

    @Bean
    LoginUseCase loginUseCase(UserRepository users, PasswordHasher passwordHasher, AccessTokenIssuer tokenIssuer) {
        return new LoginUseCaseImpl(users, passwordHasher, tokenIssuer);
    }

    @Bean
    LogoutUseCase logoutUseCase(RevokedTokenRepository revokedTokens) {
        return new LogoutUseCaseImpl(revokedTokens);
    }

    @Bean
    SignUpForEventUseCase signUpForEventUseCase(EventRepository events, UserRepository users,
                                                EventParticipantRepository participants, Clock clock) {
        return new SignUpForEventUseCaseImpl(events, users, participants, clock);
    }
}
