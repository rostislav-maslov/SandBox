package tech.maslov.sandbox.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.maslov.sandbox.base.api.response.BaseApiResponse;
import tech.maslov.sandbox.base.api.response.ListApiResponse;
import tech.maslov.sandbox.restaurant.api.response.RestaurantApiResponse;
import tech.maslov.sandbox.restaurant.routes.RestaurantApiRoutes;
import tech.maslov.sandbox.restaurant.services.RestaurantApiService;

import java.util.List;

@RestController
public class RestaurantApiController {

    @Autowired private RestaurantApiService restaurantApiService;

    @RequestMapping(value = RestaurantApiRoutes.ALL, method = RequestMethod.GET)
    public BaseApiResponse<List<RestaurantApiResponse>> all() {
        List<RestaurantApiResponse> items = restaurantApiService.all();

        return BaseApiResponse.of(
                ListApiResponse.of(items, Long.valueOf(items.size()))
        );
    }
}
