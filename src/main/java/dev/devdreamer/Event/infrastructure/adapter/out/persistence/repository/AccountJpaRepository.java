package dev.devdreamer.Event.infrastructure.adapter.out.persistence.repository;

import dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {}
