package tech.maslov.sandbox.notification.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import com.ub.core.base.models.BaseModel;


import javax.persistence.Id;

@Document
public class NotificationDoc extends BaseModel {
    public enum STATUS{
        NOT_READ, READ
    }
    @Id
    private ObjectId id;
    private ObjectId clientId;
	private String title;
	private String description;
	private ObjectId orderId;
	private STATUS readStatus = STATUS.NOT_READ;
	
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public ObjectId getClientId() {
        return clientId;
    }

    public void setClientId(ObjectId clientId) {
        this.clientId = clientId;
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

    public ObjectId getOrderId() {
        return orderId;
    }

    public void setOrderId(ObjectId orderId) {
        this.orderId = orderId;
    }

    public STATUS getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(STATUS readStatus) {
        this.readStatus = readStatus;
    }

}
