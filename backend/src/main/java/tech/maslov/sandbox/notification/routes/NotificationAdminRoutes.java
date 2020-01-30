package tech.maslov.sandbox.notification.routes;

import com.ub.core.base.routes.BaseRoutes;

public class NotificationAdminRoutes {
    private static final String ROOT = BaseRoutes.ADMIN + "/notification";

    public static final String ADD = ROOT + "/add";
    public static final String EDIT = ROOT + "/edit";
    public static final String ALL = ROOT + "/all";
    public static final String DELETE = ROOT + "/delete";
    public static final String MODAL_PARENT = ROOT + "/modal/response";

}
