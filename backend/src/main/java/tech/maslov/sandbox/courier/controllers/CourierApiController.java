package tech.maslov.sandbox.courier.controllers;

import com.ub.core.security.service.exceptions.UserNotAutorizedException;
import com.ub.core.user.service.exceptions.UserNotExistException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maslov.sandbox.base.api.response.BaseApiResponse;
import tech.maslov.sandbox.base.api.response.BaseErrorResponse;
import tech.maslov.sandbox.base.api.response.ListApiResponse;
import tech.maslov.sandbox.courier.api.request.CourierLoginApiRequest;
import tech.maslov.sandbox.courier.api.response.CourierApiResponse;
import tech.maslov.sandbox.courier.api.response.CourierLoginApiResponse;
import tech.maslov.sandbox.courier.routes.CourierApiRoutes;
import tech.maslov.sandbox.courier.services.CourierApiService;

@RestController
public class CourierApiController {
    @Autowired
    private CourierApiService courierApiService;

    @RequestMapping(value = CourierApiRoutes.LOGIN, method = RequestMethod.POST)
    public BaseApiResponse<CourierLoginApiResponse> login(@RequestBody CourierLoginApiRequest request) {
        try {
            return BaseApiResponse.of(courierApiService.login(request));
        } catch (UserNotAutorizedException e) {
            return BaseApiResponse.of(BaseApiResponse.error(BaseErrorResponse.auth()));
        }
    }

    @RequestMapping(value = CourierApiRoutes.GET, method = RequestMethod.GET)
    public BaseApiResponse<CourierApiResponse> get(@PathVariable ObjectId courierId) {
        try {
            return BaseApiResponse.of(courierApiService.get(courierId));
        } catch (UserNotExistException e) {
            return BaseApiResponse.of(BaseApiResponse.error(BaseErrorResponse.userNotFound()));
        }
    }

    @RequestMapping(value = CourierApiRoutes.ROOT, method = RequestMethod.GET)
    public BaseApiResponse<ListApiResponse<CourierApiResponse>> list(
            @RequestParam(defaultValue = "20", required = false) Integer size,
            @RequestParam(defaultValue = "0", required = false) Integer skip) {
        try {
            return BaseApiResponse.of(courierApiService.list(size, skip));
        } catch (UserNotExistException e) {
            return BaseApiResponse.of(BaseApiResponse.error(BaseErrorResponse.userNotFound()));
        }
    }

}
