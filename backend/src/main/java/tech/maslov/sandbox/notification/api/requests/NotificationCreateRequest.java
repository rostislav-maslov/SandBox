package tech.maslov.sandbox.notification.api.requests;

import com.ub.core.json.mapper.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class NotificationCreateRequest {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId clientId;
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId orderId;
    private String title;
    private String description;

    public ObjectId getClientId() {
        return clientId;
    }

    public void setClientId(ObjectId clientId) {
        this.clientId = clientId;
    }

    public ObjectId getOrderId() {
        return orderId;
    }

    public void setOrderId(ObjectId orderId) {
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
}
