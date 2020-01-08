package tech.maslov.sandbox.category.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.maslov.sandbox.base.api.response.BaseApiResponse;
import tech.maslov.sandbox.base.api.response.ListApiResponse;
import tech.maslov.sandbox.category.api.response.CategoryApiResponse;
import tech.maslov.sandbox.category.models.CategoryDoc;
import tech.maslov.sandbox.category.routes.CategoryApiRoutes;
import tech.maslov.sandbox.category.services.CategoryApiService;

import java.util.List;

@RestController
public class CategoryApiController {
    @Autowired
    private CategoryApiService categoryApiService;

    @RequestMapping(value = CategoryApiRoutes.ALL, method = RequestMethod.GET)
    public BaseApiResponse<List<CategoryApiResponse>> all() {
        List<CategoryApiResponse> items = categoryApiService.all();

        return BaseApiResponse.of(
                ListApiResponse.of(items, Long.valueOf(items.size()))
        );
    }
}
