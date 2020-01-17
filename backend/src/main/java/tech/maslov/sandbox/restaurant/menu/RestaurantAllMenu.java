package tech.maslov.sandbox.restaurant.menu;

import tech.maslov.sandbox.restaurant.routes.RestaurantAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class RestaurantAllMenu extends CoreMenu {
    public RestaurantAllMenu() {
        this.name = "Все";
        this.parent = new RestaurantMenu();
        this.url = RestaurantAdminRoutes.ALL;
    }
}
