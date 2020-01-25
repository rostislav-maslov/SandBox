package tech.maslov.sandbox.courier.services;

import com.ub.core.security.service.exceptions.UserNotAutorizedException;
import com.ub.core.user.models.UserDoc;
import com.ub.core.user.models.UserToken;
import com.ub.core.user.service.TokenSessionService;
import com.ub.core.user.service.UserService;
import com.ub.core.user.service.exceptions.UserNotExistException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import tech.maslov.sandbox.base.api.response.ListApiResponse;
import tech.maslov.sandbox.courier.api.request.CourierLoginApiRequest;
import tech.maslov.sandbox.courier.api.response.CourierApiResponse;
import tech.maslov.sandbox.courier.api.response.CourierLoginApiResponse;
import tech.maslov.sandbox.courier.models.CourierDoc;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourierApiService {
    @Autowired
    private CourierService courierService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenSessionService tokenSessionService;

    public CourierLoginApiResponse login(CourierLoginApiRequest request) throws UserNotAutorizedException {
        CourierDoc courierDoc = courierService.create(
                request.getPhoneNumber(),
                request.getFirstName(),
                request.getLastName()
        );
        UserDoc userDoc = userService.getUser(courierDoc.getId());
        CourierLoginApiResponse response = new CourierLoginApiResponse();

        if (courierDoc.getPhoneNumber().toString().contains(request.getCode().toString())) {
            //SUCCESS
            UserToken userToken = tokenSessionService.getToken(userDoc);
            response.setAccessToken(userToken.getToken());
            response.setId(courierDoc.getId().toString());
        } else {
            throw new UserNotAutorizedException();
        }
        return response;
    }

    public static CourierApiResponse transform(CourierDoc courierDoc){
        CourierApiResponse courierApiResponse = new CourierApiResponse();

        courierApiResponse.setId(courierDoc.getId().toString());
        courierApiResponse.setFirstName(courierDoc.getFirstName());
        courierApiResponse.setLastName(courierDoc.getLastName());
        courierApiResponse.setPhoneNumber(courierDoc.getPhoneNumber());

        return courierApiResponse;
    }

    public CourierApiResponse get(ObjectId courierId) throws UserNotExistException {
        CourierDoc courierDoc = courierService.findById(courierId);

        if(courierDoc == null) throw new UserNotExistException();

        return transform(courierDoc);
    }

    public ListApiResponse<CourierApiResponse> list(Integer size, Integer skip) throws UserNotExistException {
        List<CourierDoc> courierDocs = courierService.findAll(size, skip);
        ListApiResponse<CourierApiResponse> response = new ListApiResponse<>();
        response.setItems(new ArrayList<CourierApiResponse>());

        for(CourierDoc courierDoc : courierDocs){
            response.getItems().add(transform(courierDoc));
        }

        response.setTotal(courierService.count(new Query()));

        return response;
    }
}
