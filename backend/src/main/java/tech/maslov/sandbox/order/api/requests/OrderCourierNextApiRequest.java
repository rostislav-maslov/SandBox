package tech.maslov.sandbox.order.api.requests;

import com.ub.core.json.mapper.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import tech.maslov.sandbox.base.models.Point;
import tech.maslov.sandbox.order.models.DeliveryInfo;

public class OrderCourierNextApiRequest {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId courierId;

    public ObjectId getCourierId() {
        return courierId;
    }

    public void setCourierId(ObjectId courierId) {
        this.courierId = courierId;
    }
}
