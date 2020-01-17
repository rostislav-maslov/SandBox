package tech.maslov.sandbox.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.maslov.sandbox.base.api.response.BaseApiResponse;
import tech.maslov.sandbox.base.api.response.ListApiResponse;
import tech.maslov.sandbox.category.api.response.CategoryApiResponse;
import tech.maslov.sandbox.product.api.response.ProductApiResponse;
import tech.maslov.sandbox.product.routes.ProductApiRoutes;
import tech.maslov.sandbox.product.services.ProductApiService;
import tech.maslov.sandbox.restaurant.routes.RestaurantApiRoutes;

import java.util.List;

@RestController
public class ProductApiController {

    @Autowired private ProductApiService productApiService;

    @RequestMapping(value = ProductApiRoutes.ALL, method = RequestMethod.GET)
    public BaseApiResponse<List<CategoryApiResponse>> all() {
        List<ProductApiResponse> items = productApiService.all();

        return BaseApiResponse.of(
                ListApiResponse.of(items, Long.valueOf(items.size()))
        );
    }
}
