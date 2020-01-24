package tech.maslov.sandbox.client.menu;

import tech.maslov.sandbox.client.routes.ClientAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class ClientAddMenu extends CoreMenu {
    public ClientAddMenu() {
        this.name = "Добавить";
        this.parent = new ClientMenu();
        this.url = ClientAdminRoutes.ADD;
    }
}
