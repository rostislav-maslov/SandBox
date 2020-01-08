package tech.maslov.sandbox.category.menu;

import tech.maslov.sandbox.category.routes.CategoryAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class CategoryAllMenu extends CoreMenu {
    public CategoryAllMenu() {
        this.name = "Все";
        this.parent = new CategoryMenu();
        this.url = CategoryAdminRoutes.ALL;
    }
}
