package tech.maslov.sandbox.courier.roles;

import com.ub.core.base.role.Role;
import com.ub.core.user.routes.UserLoginRoutes;

public class CourierRole extends Role {
    public CourierRole() {
        id = this.getClass().getName();
        roleTitle = "Курьер";
        roleDescription = "Курьеры";
        goAfterFail = UserLoginRoutes.LOGIN;
    }
}
