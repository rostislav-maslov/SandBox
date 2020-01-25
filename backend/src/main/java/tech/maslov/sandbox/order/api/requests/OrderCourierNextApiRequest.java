package tech.maslov.sandbox.order.api.requests;

import org.bson.types.ObjectId;
import tech.maslov.sandbox.base.models.Point;
import tech.maslov.sandbox.order.models.DeliveryInfo;

public class OrderCourierNextApiRequest {

    private ObjectId courierId;

    public ObjectId getCourierId() {
        return courierId;
    }

    public void setCourierId(ObjectId courierId) {
        this.courierId = courierId;
    }
}
