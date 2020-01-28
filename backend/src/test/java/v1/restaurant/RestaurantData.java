package v1.restaurant;

import org.bson.types.ObjectId;
import tech.maslov.sandbox.restaurant.models.RestaurantDoc;
import tech.maslov.sandbox.restaurant.services.RestaurantService;

public class RestaurantData {

    private RestaurantService restaurantService;

    public RestaurantDoc rCenter;
    public RestaurantDoc rRed;

    public RestaurantData(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public static RestaurantData instance(RestaurantService restaurantService){
        return new RestaurantData(restaurantService);
    }

    public RestaurantData init() {
        this.rCenter = initRestaurant("В Центре", "Ресторан в центре");
        this.rRed = initRestaurant("На Красном", "Ресторан на красном");

        return this;
    }

    public RestaurantDoc initRestaurant(String title, String description) {
        RestaurantDoc doc = new RestaurantDoc();

        doc.setTitle(title);
        doc.setDescription(description);
        doc.setPicId(new ObjectId());
        doc.getPoint().setLatitude(0.0);
        doc.getPoint().setLongitude(0.0);

        return restaurantService.save(doc);
    }

}
