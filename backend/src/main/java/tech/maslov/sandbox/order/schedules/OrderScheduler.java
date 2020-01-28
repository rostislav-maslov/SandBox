package tech.maslov.sandbox.order.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tech.maslov.sandbox.order.services.OrderSchedulerService;

@Service
public class OrderScheduler {

    @Autowired private OrderSchedulerService orderSchedulerService;

    @Scheduled(fixedDelay = 60 * 1000l, initialDelay = 1000l * 65)
    public void setRestaurant(){
        orderSchedulerService.setRestaurant();
    }

    @Scheduled(fixedDelay = 2 * 60 * 1000l, initialDelay = 1000l * 60)
    public void moveOrderStatus(){
        orderSchedulerService.moveOrdersStatus();
    }

}
