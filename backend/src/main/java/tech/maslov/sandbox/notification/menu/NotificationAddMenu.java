package tech.maslov.sandbox.notification.menu;

import tech.maslov.sandbox.notification.routes.NotificationAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class NotificationAddMenu extends CoreMenu {
    public NotificationAddMenu() {
        this.name = "Добавить";
        this.parent = new NotificationMenu();
        this.url = NotificationAdminRoutes.ADD;
    }
}
