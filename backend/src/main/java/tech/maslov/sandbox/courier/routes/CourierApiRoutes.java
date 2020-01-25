package tech.maslov.sandbox.courier.routes;

import tech.maslov.sandbox.base.routes.BaseApiRouter;

public class CourierApiRoutes {
    public static final String ROOT = BaseApiRouter.V1 + "/courier";

    public static final String LOGIN = ROOT + "/login";
    public static final String GET = ROOT + "/{courierId}";

}
