package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.NotificationTemplate;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TemplateRepository extends JpaRepository<NotificationTemplate, UUID> {

    @Query("""
            SELECT nt FROM NotificationTemplate nt
            WHERE nt.createdBy.userId = :creatorId
            """)
    Page<NotificationTemplate> getTemplatesByCreatorId(UUID creatorId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("""
            DELETE FROM NotificationTemplate nt
            WHERE nt.templateId = :templateId
            AND nt.createdBy.userId = :userId
            """)
    int deleteTemplateById(UUID templateId, UUID userId);
}
