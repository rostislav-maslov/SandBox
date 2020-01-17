package tech.maslov.sandbox.restaurant.events;

import tech.maslov.sandbox.restaurant.models.RestaurantDoc;

public interface IRestaurantEvent {
    public void preSave(RestaurantDoc doc);
    public void afterSave(RestaurantDoc doc);

    public Boolean preDelete(RestaurantDoc doc);
    public void afterDelete(RestaurantDoc doc);
}
