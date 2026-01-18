package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.OutBoxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutBoxEventRepository extends JpaRepository<OutBoxEvent, UUID> {
}
