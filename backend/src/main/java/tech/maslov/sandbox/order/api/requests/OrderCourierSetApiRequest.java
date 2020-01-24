package tech.maslov.sandbox.order.api.requests;

import org.bson.types.ObjectId;
import tech.maslov.sandbox.base.models.Point;
import tech.maslov.sandbox.order.models.DeliveryInfo;

import java.util.ArrayList;
import java.util.List;

public class OrderCourierSetApiRequest {

    private ObjectId id;
    private ObjectId courierId;
    private DeliveryInfo.STATUS status;
    private Point currentPosition;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getCourierId() {
        return courierId;
    }

    public void setCourierId(ObjectId courierId) {
        this.courierId = courierId;
    }

    public DeliveryInfo.STATUS getStatus() {
        return status;
    }

    public void setStatus(DeliveryInfo.STATUS status) {
        this.status = status;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }
}
