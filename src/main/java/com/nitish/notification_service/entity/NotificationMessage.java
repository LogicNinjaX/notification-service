package com.nitish.notification_service.entity;

import com.nitish.notification_service.enums.MessageStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "notification_message_table",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_request_recipient", columnNames = {"request_id", "recipient"})
        }
)
public class NotificationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID messageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_id", referencedColumnName = "requestId", nullable = false)
    private NotificationRequest request;

    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<NotificationDelivery> notificationDeliveries = new ArrayList<>();

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = true)
    private String renderedSubject; // of email only

    @Column(columnDefinition = "TEXT", nullable = false)
    private String renderedContent;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @Column(nullable = false)
    private int retryCount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public NotificationRequest getRequest() {
        return request;
    }

    public void setRequest(NotificationRequest request) {
        this.request = request;
    }

    public List<NotificationDelivery> getNotificationDeliveries() {
        return notificationDeliveries;
    }

    public void setNotificationDeliveries(List<NotificationDelivery> notificationDeliveries) {
        this.notificationDeliveries = notificationDeliveries;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRenderedSubject() {
        return renderedSubject;
    }

    public void setRenderedSubject(String renderedSubject) {
        this.renderedSubject = renderedSubject;
    }

    public String getRenderedContent() {
        return renderedContent;
    }

    public void setRenderedContent(String renderedContent) {
        this.renderedContent = renderedContent;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
