package tech.maslov.sandbox.notification.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tech.maslov.sandbox.client.services.ClientService;
import tech.maslov.sandbox.notification.routes.NotificationRoutes;
import tech.maslov.sandbox.notification.services.NotificationApiService;

@Controller
public class NotificationClientController {
    @Autowired
    private NotificationApiService notificationApiService;
    @Autowired private ClientService clientService;

    @RequestMapping(value = NotificationRoutes.ROOT, method = RequestMethod.GET)
    public String clientNot(@RequestParam ObjectId clientId, Model model){
        model.addAttribute("notifications", notificationApiService.findByClient(clientId));

        model.addAttribute("clientDoc", clientService.findById(clientId));
        model.addAttribute("notifications", notificationApiService.findByClient(clientId));

        return "tech.maslov.sandbox.client.notification.client";
    }
}
