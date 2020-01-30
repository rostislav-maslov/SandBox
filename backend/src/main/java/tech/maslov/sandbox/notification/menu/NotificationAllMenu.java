package tech.maslov.sandbox.notification.menu;

import tech.maslov.sandbox.notification.routes.NotificationAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class NotificationAllMenu extends CoreMenu {
    public NotificationAllMenu() {
        this.name = "Все";
        this.parent = new NotificationMenu();
        this.url = NotificationAdminRoutes.ALL;
    }
}
