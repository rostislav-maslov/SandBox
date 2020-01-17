package tech.maslov.sandbox.restaurant.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maslov.sandbox.product.api.response.ProductApiResponse;
import tech.maslov.sandbox.product.models.ProductDoc;
import tech.maslov.sandbox.product.services.ProductService;
import tech.maslov.sandbox.restaurant.api.response.RestaurantApiResponse;
import tech.maslov.sandbox.restaurant.models.RestaurantDoc;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantApiService {
    @Autowired
    private RestaurantService restaurantService;

    public RestaurantApiResponse transform(RestaurantDoc doc){
        RestaurantApiResponse response = new RestaurantApiResponse();

        response.setId(doc.getId().toString());
        response.setTitle(doc.getTitle());
        response.setDescription(doc.getDescription());
        response.setPicId(doc.getPicId() != null ? doc.getPicId().toString() : null);
        response.setAvailable(doc.getAvailable());
        response.setPoint(doc.getPoint());

        return response;
    }

    public List<RestaurantApiResponse> all(){
        List<RestaurantDoc> products = restaurantService.findAll();

        List<RestaurantApiResponse> responses = new ArrayList<>();
        for(RestaurantDoc restaurantDoc: products){
            responses.add(transform(restaurantDoc));
        }

        return responses;
    }
}
