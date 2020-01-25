package tech.maslov.sandbox.order.routes;

import tech.maslov.sandbox.base.routes.BaseApiRouter;

public class OrderApiRoutes {
    public static final String ROOT = BaseApiRouter.V1 + "/order";

    public static final String CREATE = ROOT + "/create";
    public static final String PAY = CREATE + "/pay";
    public static final String COURIER = ROOT + "/courier";
    public static final String COURIER_NEXT = COURIER + "/next";
    public static final String COURIER_CLOSE_ALL = COURIER + "/close";
}
