package tech.maslov.sandbox.category.menu;

import tech.maslov.sandbox.category.routes.CategoryAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class CategoryAddMenu extends CoreMenu {
    public CategoryAddMenu() {
        this.name = "Добавить";
        this.parent = new CategoryMenu();
        this.url = CategoryAdminRoutes.ADD;
    }
}
