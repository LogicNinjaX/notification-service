package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.OutBoxEvent;
import com.nitish.notification_service.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OutBoxEventRepository extends JpaRepository<OutBoxEvent, UUID> {

    @Query("""
            SELECT obe FROM OutBoxEvent obe
            WHERE obe.status = :eventStatus
            LIMIT = 50
            """)
    List<OutBoxEvent> getEventsByStatus(EventStatus eventStatus);

    @Modifying
    @Query("""
            UPDATE OutBoxEvent obe
            SET obe.status = :eventStatus
            WHERE obe.eventId = :eventId
            """)
    void updateEventStatus(UUID eventId, EventStatus eventStatus);
}
