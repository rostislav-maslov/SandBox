package tech.maslov.sandbox.order.menu;

import tech.maslov.sandbox.order.routes.OrderAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class OrderAddMenu extends CoreMenu {
    public OrderAddMenu() {
        this.name = "Добавить";
        this.parent = new OrderMenu();
        this.url = OrderAdminRoutes.ADD;
    }
}
