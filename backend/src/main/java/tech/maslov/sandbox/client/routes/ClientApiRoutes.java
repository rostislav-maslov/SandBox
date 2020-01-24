package tech.maslov.sandbox.client.routes;

import tech.maslov.sandbox.base.routes.BaseApiRouter;

public class ClientApiRoutes {
    public static final String ROOT = BaseApiRouter.V1 + "/client";

    public static final String LOGIN = ROOT + "/login";
    public static final String GET = ROOT + "/{clientId}";


}
