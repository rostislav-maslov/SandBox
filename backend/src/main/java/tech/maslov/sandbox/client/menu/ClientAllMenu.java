package tech.maslov.sandbox.client.menu;

import tech.maslov.sandbox.client.routes.ClientAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class ClientAllMenu extends CoreMenu {
    public ClientAllMenu() {
        this.name = "Все";
        this.parent = new ClientMenu();
        this.url = ClientAdminRoutes.ALL;
    }
}
