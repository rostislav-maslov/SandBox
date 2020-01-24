package tech.maslov.sandbox.courier.menu;

import tech.maslov.sandbox.courier.routes.CourierAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class CourierAddMenu extends CoreMenu {
    public CourierAddMenu() {
        this.name = "Добавить";
        this.parent = new CourierMenu();
        this.url = CourierAdminRoutes.ADD;
    }
}
