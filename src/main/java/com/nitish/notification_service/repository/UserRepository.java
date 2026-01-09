package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.User;
import com.nitish.notification_service.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Modifying
    @Query("""
            DELETE FROM User u
            WHERE u.userId = :userId
            """)
    int deleteUser(UUID userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.role = :userRole
            WHERE u.userId = :userId
            """)
    int updateUserRole(UUID userId, UserRole userRole);
}
