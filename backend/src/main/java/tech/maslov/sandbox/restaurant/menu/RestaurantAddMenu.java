package tech.maslov.sandbox.restaurant.menu;

import tech.maslov.sandbox.restaurant.routes.RestaurantAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class RestaurantAddMenu extends CoreMenu {
    public RestaurantAddMenu() {
        this.name = "Добавить";
        this.parent = new RestaurantMenu();
        this.url = RestaurantAdminRoutes.ADD;
    }
}
