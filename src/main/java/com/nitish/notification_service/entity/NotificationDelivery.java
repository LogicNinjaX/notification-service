package com.nitish.notification_service.entity;

import com.nitish.notification_service.enums.DeliveryStatus;
import com.nitish.notification_service.enums.NotificationChannel;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class NotificationDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID deliveryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "message_id", referencedColumnName = "messageId", nullable = false)
    private NotificationMessage message;

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private int attemptNumber;

    @Column(columnDefinition = "TEXT")
    private String providerResponse;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public UUID getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public NotificationMessage getMessage() {
        return message;
    }

    public void setMessage(NotificationMessage message) {
        this.message = message;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public String getProviderResponse() {
        return providerResponse;
    }

    public void setProviderResponse(String providerResponse) {
        this.providerResponse = providerResponse;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static NotificationDelivery success(NotificationMessage msg, NotificationChannel channel, int attempt) {
        NotificationDelivery delivery = new NotificationDelivery();
        delivery.setMessage(msg);
        delivery.setChannel(channel);
        delivery.setStatus(DeliveryStatus.SUCCESS);
        delivery.setAttemptNumber(attempt);
        delivery.setSentAt(LocalDateTime.now());
        return delivery;
    }

    public static NotificationDelivery failure(NotificationMessage msg, NotificationChannel channel, String response, int attempt) {
        NotificationDelivery delivery = new NotificationDelivery();
        delivery.setMessage(msg);
        delivery.setChannel(channel);
        delivery.setStatus(DeliveryStatus.FAILED);
        delivery.setAttemptNumber(attempt);
        delivery.setProviderResponse(response);
        delivery.setSentAt(LocalDateTime.now());
        return delivery;
    }

}
