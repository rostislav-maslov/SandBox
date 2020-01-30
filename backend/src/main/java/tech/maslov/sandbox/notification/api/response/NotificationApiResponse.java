package tech.maslov.sandbox.notification.api.response;

import tech.maslov.sandbox.notification.models.NotificationDoc;

import java.util.Date;

public class NotificationApiResponse {
    private String id;
    private String clientId;
    private String orderId;
    private String title;
    private String description;
    private NotificationDoc.STATUS readStatus;
    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NotificationDoc.STATUS getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(NotificationDoc.STATUS readStatus) {
        this.readStatus = readStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
