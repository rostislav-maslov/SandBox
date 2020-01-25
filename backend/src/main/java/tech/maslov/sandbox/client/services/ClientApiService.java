package tech.maslov.sandbox.client.services;

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
import tech.maslov.sandbox.client.api.request.ClientLoginApiRequest;
import tech.maslov.sandbox.client.api.response.ClientApiResponse;
import tech.maslov.sandbox.client.api.response.ClientLoginApiResponse;
import tech.maslov.sandbox.client.models.ClientDoc;
import tech.maslov.sandbox.client.models.Sex;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientApiService {
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenSessionService tokenSessionService;

    public ClientLoginApiResponse login(ClientLoginApiRequest request) throws UserNotAutorizedException {
        ClientDoc clientDoc = clientService.create(request.getPhoneNumber());
        UserDoc userDoc = userService.getUser(clientDoc.getId());
        ClientLoginApiResponse response = new ClientLoginApiResponse();

        if (clientDoc.getPhoneNumber().toString().contains(request.getCode().toString())) {
            //SUCCESS
            UserToken userToken = tokenSessionService.getToken(userDoc);
            response.setAccessToken(userToken.getToken());
            response.setId(clientDoc.getId().toString());
        } else {
            throw new UserNotAutorizedException();
        }
        return response;
    }

    public static ClientApiResponse transform(ClientDoc clientDoc){
        ClientApiResponse clientApiResponse = new ClientApiResponse();

        clientApiResponse.setId(clientDoc.getId().toString());
        clientApiResponse.setFirstName(clientDoc.getFirstName());
        clientApiResponse.setLastName(clientDoc.getLastName());
        clientApiResponse.setPhoneNumber(clientDoc.getPhoneNumber());
        clientApiResponse.setSex(clientDoc.getSex());

        clientApiResponse.setBDate(clientDoc.getbDate());

        return clientApiResponse;
    }

    public ClientApiResponse get(ObjectId clientId) throws UserNotExistException {
        ClientDoc clientDoc = clientService.findById(clientId);

        if(clientDoc == null) throw new UserNotExistException();

        return transform(clientDoc);
    }

    public ClientDoc exampleClient(){
        Long phoneNumber = 79607777777l;

        ClientDoc clientDoc = clientService.findByPhoneNumber(phoneNumber);
        if(clientDoc == null){
            ClientLoginApiRequest clientLoginApiRequest = new ClientLoginApiRequest(phoneNumber, 7777);
            try {
                ClientLoginApiResponse response = login(clientLoginApiRequest);
                clientDoc = clientService.findById(new ObjectId(response.getId()));
                clientDoc.setFirstName("Иван");
                clientDoc.setLastName("Иванов");
                clientDoc.setBDate("1990-10-01");
                clientDoc.setSex(Sex.MALE);
                clientService.save(clientDoc);
            } catch (UserNotAutorizedException ignore) {
            }
        }

        return clientDoc;
    }

    public ListApiResponse<ClientApiResponse> list(Integer size, Integer skip) throws UserNotExistException {
        List<ClientDoc> clientDocs = clientService.findAll(size, skip);
        ListApiResponse<ClientApiResponse> response = new ListApiResponse<>();
        response.setItems(new ArrayList<ClientApiResponse>());

        for(ClientDoc clientDoc : clientDocs){
            response.getItems().add(transform(clientDoc));
        }

        response.setTotal(clientService.count(new Query()));

        return response;
    }
}
