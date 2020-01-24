package tech.maslov.sandbox.courier.menu;

import tech.maslov.sandbox.courier.routes.CourierAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class CourierAllMenu extends CoreMenu {
    public CourierAllMenu() {
        this.name = "Все";
        this.parent = new CourierMenu();
        this.url = CourierAdminRoutes.ALL;
    }
}
