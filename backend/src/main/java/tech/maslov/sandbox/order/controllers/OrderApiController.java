package tech.maslov.sandbox.order.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.maslov.sandbox.base.api.response.BaseApiResponse;
import tech.maslov.sandbox.base.api.response.ListApiResponse;
import tech.maslov.sandbox.order.api.requests.OrderCourierNextApiRequest;
import tech.maslov.sandbox.order.api.requests.OrderCourierSetApiRequest;
import tech.maslov.sandbox.order.api.requests.OrderCreateApiRequest;
import tech.maslov.sandbox.order.api.responses.OrderApiResponse;
import tech.maslov.sandbox.order.routes.OrderApiRoutes;
import tech.maslov.sandbox.order.services.OrderApiService;

@RestController
public class OrderApiController {
    @Autowired private OrderApiService orderApiService;

    @RequestMapping(value = OrderApiRoutes.CREATE, method = RequestMethod.POST)
    public BaseApiResponse<OrderApiResponse> create(@RequestBody OrderCreateApiRequest request){
        return BaseApiResponse.of(orderApiService.create(request));
    }

    @RequestMapping(value = OrderApiRoutes.ROOT, method = RequestMethod.GET)
    public BaseApiResponse<ListApiResponse<OrderApiResponse>> list(
            @RequestParam(defaultValue = "20", required = false) Integer size,
            @RequestParam(defaultValue = "0", required = false) Integer skip){
        return BaseApiResponse.of(orderApiService.list(size, skip));
    }

    @RequestMapping(value = OrderApiRoutes.COURIER, method = RequestMethod.GET)
    public BaseApiResponse<ListApiResponse<OrderApiResponse>> courier(
            @RequestParam ObjectId courierId,
            @RequestParam(defaultValue = "20", required = false) Integer size,
            @RequestParam(defaultValue = "0", required = false) Integer skip
    ){
        return BaseApiResponse.of(orderApiService.courierOrders(courierId, size, skip));
    }

    @RequestMapping(value = OrderApiRoutes.COURIER, method = RequestMethod.POST)
    public BaseApiResponse<OrderApiResponse> courierSet(@RequestBody OrderCourierSetApiRequest request){
        return BaseApiResponse.of(orderApiService.courierSet(request));
    }

    @RequestMapping(value = OrderApiRoutes.COURIER_NEXT, method = RequestMethod.POST)
    public BaseApiResponse<OrderApiResponse> courierNextOrder(@RequestBody OrderCourierNextApiRequest request){
        return BaseApiResponse.of(orderApiService.courierNext(request));
    }

    @RequestMapping(value = OrderApiRoutes.COURIER_CLOSE_ALL, method = RequestMethod.POST)
    public BaseApiResponse<String> courierCloseAll(@RequestBody OrderCourierNextApiRequest request){
        orderApiService.closeAll(request);
        return BaseApiResponse.of(HttpStatus.OK.getReasonPhrase());
    }
}
