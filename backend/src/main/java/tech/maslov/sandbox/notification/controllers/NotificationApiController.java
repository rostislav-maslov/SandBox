package tech.maslov.sandbox.notification.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maslov.sandbox.base.api.response.BaseApiResponse;
import tech.maslov.sandbox.notification.api.requests.NotificationCreateRequest;
import tech.maslov.sandbox.notification.api.response.NotificationApiResponse;
import tech.maslov.sandbox.notification.routes.NotificationApiRoutes;
import tech.maslov.sandbox.notification.services.NotificationApiService;

import java.util.List;

@RestController
public class NotificationApiController {

    @Autowired private NotificationApiService notificationApiService;

    @RequestMapping(value = NotificationApiRoutes.ROOT, method = RequestMethod.GET)
    public BaseApiResponse<List<NotificationApiResponse>> list(@RequestParam ObjectId clientId){
        return BaseApiResponse.of(notificationApiService.findByClient(clientId));
    }

    @RequestMapping(value = NotificationApiRoutes.ROOT, method = RequestMethod.POST)
    public BaseApiResponse<NotificationApiResponse> create(@RequestBody NotificationCreateRequest request){
        return BaseApiResponse.of(notificationApiService.create(request));
    }
}
