package tech.maslov.sandbox.order.routes;

import tech.maslov.sandbox.base.routes.BaseApiRouter;

public class OrderApiRoutes {
    public static final String ROOT = BaseApiRouter.V1 + "/order";

    public static final String CREATE = ROOT + "/create";
    public static final String PAY = CREATE + "/pay";
}
