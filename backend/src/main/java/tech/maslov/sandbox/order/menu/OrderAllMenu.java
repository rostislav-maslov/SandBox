package tech.maslov.sandbox.order.menu;

import tech.maslov.sandbox.order.routes.OrderAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class OrderAllMenu extends CoreMenu {
    public OrderAllMenu() {
        this.name = "Все";
        this.parent = new OrderMenu();
        this.url = OrderAdminRoutes.ALL;
    }
}
