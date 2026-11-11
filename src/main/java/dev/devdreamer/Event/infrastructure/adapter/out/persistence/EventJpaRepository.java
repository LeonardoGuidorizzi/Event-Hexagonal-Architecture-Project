package dev.devdreamer.Event.infrastructure.adapter.out.persistence;

import dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import dev.devdreamer.Event.domain.enums.EventStatus;

import java.util.UUID;

public interface EventJpaRepository extends JpaRepository<EventEntity, UUID> {
    @Query(value = """
        SELECT * FROM tb_event
        WHERE (:status IS NULL OR status = :status)
          AND (:name IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%', :name, '%')))
        ORDER BY starts_at ASC, id ASC
        """,
            countQuery = """
        SELECT COUNT(*) FROM tb_event
        WHERE (:status IS NULL OR status = :status)
          AND (:name IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%', :name, '%')))
        """,
            nativeQuery = true)
    Page<EventEntity> findAllFiltered(@Param("status") String status, @Param("name") String name, Pageable pageable);


}
