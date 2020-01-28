package tech.maslov.sandbox.order.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maslov.sandbox.order.models.DeliveryInfo;
import tech.maslov.sandbox.order.models.OrderDoc;
import tech.maslov.sandbox.restaurant.models.RestaurantDoc;
import tech.maslov.sandbox.restaurant.services.RestaurantService;

import java.util.List;
import java.util.Random;

import static tech.maslov.sandbox.order.models.DeliveryInfo.STATUS.*;

@Service
public class OrderSchedulerService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RestaurantService restaurantService;

    private RestaurantDoc getRestDefault() {
        List<RestaurantDoc> restaurantDocs = restaurantService.findAll();
        if (restaurantDocs.size() == 0) return null;

        Integer integer = new Random().nextInt(restaurantDocs.size());
        if (integer < restaurantDocs.size()) return restaurantDocs.get(integer);

        return getRestDefault();
    }

    public void setRestaurant() {
        List<OrderDoc> orderDocs = orderService.findByRestaurant();
        RestaurantDoc restaurantDoc = getRestDefault();

        for (OrderDoc orderDoc : orderDocs) {
            orderDoc.getDeliveryInfo().setRestaurantId(restaurantDoc.getId());
            orderDoc.getDeliveryInfo().setCurrentPosition(restaurantDoc.getPoint());
            orderService.save(orderDoc);
        }
    }

    public void moveOrdersStatus() {
        moveOrdersStatus(DELIVERED, CLOSED);
        moveOrdersStatus(READY, CLOSED, DeliveryInfo.TYPE.PICKUP);
        moveOrdersStatus(COOK, READY);
        moveOrdersStatus(CONFIRMED, COOK);

        //CONFIRMED, COOK, READY, ON_THE_WAY, DELIVERED, CLOSED
    }

    public void moveOrdersStatus(DeliveryInfo.STATUS from, DeliveryInfo.STATUS to) {
        List<OrderDoc> orderDocs = orderService.findByStatus(from);

        for (OrderDoc orderDoc : orderDocs) {
            orderDoc.getDeliveryInfo().setStatus(to);
            orderService.save(orderDoc);
        }
    }

    public void moveOrdersStatus(DeliveryInfo.STATUS from, DeliveryInfo.STATUS to, DeliveryInfo.TYPE type) {
        List<OrderDoc> orderDocs = orderService.findByStatusAndType(from, type);

        for (OrderDoc orderDoc : orderDocs) {
            orderDoc.getDeliveryInfo().setStatus(to);
            orderService.save(orderDoc);
        }
    }

}
