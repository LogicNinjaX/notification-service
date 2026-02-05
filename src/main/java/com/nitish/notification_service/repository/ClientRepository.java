package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.Client;
import com.nitish.notification_service.enums.ClientStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    @Modifying
    @Query("""
            UPDATE Client cl
            SET cl.status = :status
            WHERE cl.clientId = :clientId
            """)
    int updateClientStatus(UUID clientId, ClientStatus status);

    @Query("""
            select cl from Client cl
            where cl.status = :status
            """)
    Page<Client> findAllClientsByStatus(ClientStatus status, Pageable pageable);
}
