package tech.maslov.sandbox.order.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tech.maslov.sandbox.order.models.OrderDoc;
import tech.maslov.sandbox.order.models.PaymentInfo;
import tech.maslov.sandbox.order.routes.OrderApiRoutes;
import tech.maslov.sandbox.order.services.OrderService;

@Controller
public class OrderClientController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = OrderApiRoutes.PAY, method = RequestMethod.GET)
    public String payPage(@RequestParam ObjectId orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "tech.maslov.sandbox.client.order.pay";
    }

    @RequestMapping(value = OrderApiRoutes.PAY, method = RequestMethod.POST)
    public String payUpdate(@RequestParam ObjectId orderId, @RequestParam Boolean success, Model model) {
        OrderDoc orderDoc = orderService.findById(orderId);
        String resultText = "";
        if(success){
            orderDoc.getPaymentInfo().setStatus(PaymentInfo.STATUS.PAYED);
            resultText = "Онлайн оплата успешно проведена";
        }else {
            orderDoc.getPaymentInfo().setStatus(PaymentInfo.STATUS.NOT_PAYED);
            resultText = "Ошибка в онлайн оплате";
        }
        orderService.save(orderDoc);

        model.addAttribute("orderId", orderId);
        model.addAttribute("resultText", resultText);
        return "tech.maslov.sandbox.client.order.result";
    }


}
