package tech.maslov.sandbox.client.routes;

import com.ub.core.base.routes.BaseRoutes;

public class ClientAdminRoutes {
    private static final String ROOT = BaseRoutes.ADMIN + "/client";

    public static final String ADD = ROOT + "/add";
    public static final String EDIT = ROOT + "/edit";
    public static final String ALL = ROOT + "/all";
    public static final String DELETE = ROOT + "/delete";
    public static final String MODAL_PARENT = ROOT + "/modal/response";

}
