package tech.maslov.sandbox.product.menu;

import tech.maslov.sandbox.product.routes.ProductAdminRoutes;
import com.ub.core.base.menu.CoreMenu;

public class ProductAddMenu extends CoreMenu {
    public ProductAddMenu() {
        this.name = "Добавить";
        this.parent = new ProductMenu();
        this.url = ProductAdminRoutes.ADD;
    }
}
