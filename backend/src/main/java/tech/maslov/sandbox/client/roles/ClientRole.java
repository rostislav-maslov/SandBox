package tech.maslov.sandbox.client.roles;

import com.ub.core.base.role.Role;
import com.ub.core.user.routes.UserLoginRoutes;

public class ClientRole extends Role {
    public ClientRole() {
        id = this.getClass().getName();
        roleTitle = "Клиент";
        roleDescription = "Клиенты";
        goAfterFail = UserLoginRoutes.LOGIN;
    }
}
