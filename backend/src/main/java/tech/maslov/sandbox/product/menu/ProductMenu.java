package tech.maslov.sandbox.product.menu;

import com.ub.core.base.menu.CoreMenu;
import com.ub.core.menu.models.fields.MenuIcons;

public class ProductMenu extends CoreMenu {
    public ProductMenu() {
        this.name = "Товары";
        this.icon = MenuIcons.ENTYPO_DOC_TEXT;
    }
}
