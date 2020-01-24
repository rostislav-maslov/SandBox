package tech.maslov.sandbox.client.controllers;

import com.ub.core.security.service.exceptions.UserNotAutorizedException;
import com.ub.core.user.service.exceptions.UserNotExistException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maslov.sandbox.base.api.response.BaseApiResponse;
import tech.maslov.sandbox.base.api.response.BaseErrorResponse;
import tech.maslov.sandbox.base.api.response.ListApiResponse;
import tech.maslov.sandbox.category.routes.CategoryApiRoutes;
import tech.maslov.sandbox.client.api.request.ClientLoginApiRequest;
import tech.maslov.sandbox.client.api.response.ClientApiResponse;
import tech.maslov.sandbox.client.api.response.ClientLoginApiResponse;
import tech.maslov.sandbox.client.routes.ClientApiRoutes;
import tech.maslov.sandbox.client.services.ClientApiService;

@RestController
public class ClientApiController {
    @Autowired
    private ClientApiService clientApiService;

    @RequestMapping(value = ClientApiRoutes.LOGIN, method = RequestMethod.POST)
    public BaseApiResponse<ClientLoginApiResponse> login(@RequestBody ClientLoginApiRequest request) {
        try {
            return BaseApiResponse.of(clientApiService.login(request));
        } catch (UserNotAutorizedException e) {
            return BaseApiResponse.of(BaseApiResponse.error(BaseErrorResponse.auth()));
        }
    }

    @RequestMapping(value = ClientApiRoutes.GET, method = RequestMethod.GET)
    public BaseApiResponse<ClientApiResponse> get(@PathVariable ObjectId clientId) {
        try {
            return BaseApiResponse.of(clientApiService.get(clientId));
        } catch (UserNotExistException e) {
            return BaseApiResponse.of(BaseApiResponse.error(BaseErrorResponse.userNotFound()));
        }
    }

    @RequestMapping(value = ClientApiRoutes.ROOT, method = RequestMethod.GET)
    public BaseApiResponse<ListApiResponse<ClientApiResponse>> list(
            @RequestParam(defaultValue = "20", required = false) Integer size,
            @RequestParam(defaultValue = "0", required = false) Integer skip) {
        try {
            return BaseApiResponse.of(clientApiService.list(size, skip));
        } catch (UserNotExistException e) {
            return BaseApiResponse.of(BaseApiResponse.error(BaseErrorResponse.userNotFound()));
        }
    }

}
