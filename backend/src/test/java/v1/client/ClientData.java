package v1.client;

import com.ub.core.user.models.UserDoc;
import com.ub.core.user.service.UserService;
import com.ub.core.user.service.exceptions.UserExistException;
import org.bson.types.ObjectId;
import tech.maslov.sandbox.category.models.CategoryDoc;
import tech.maslov.sandbox.client.api.request.ClientLoginApiRequest;
import tech.maslov.sandbox.client.models.ClientDoc;
import tech.maslov.sandbox.client.services.ClientApiService;
import tech.maslov.sandbox.client.services.ClientService;

public class ClientData {
    private ClientService clientService;
    private ClientApiService clientApiService;
    private UserService userService;

    public ClientDoc getRequestData;

    public static ClientData instance(
            ClientService clientService,
            ClientApiService clientApiService,
            UserService userService){
        ClientData data =  new ClientData();
        data.clientService = clientService;
        data.clientApiService = clientApiService;
        data.userService = userService;
        return data;
    }

    public ClientData init() {
            this.getRequestData = initClient(7958736232l, "Иван", "Иванов");
            initClient(7958736231l, "Иван", "Иванов");
            initClient(7958736233l, "Иван", "Иванов");
            initClient(7958736234l, "Иван", "Иванов");
            initClient(7958736235l, "Иван", "Иванов");
            initClient(7958736236l, "Иван", "Иванов");
            initClient(7958736237l, "Иван", "Иванов");
            initClient(7958736238l, "Иван", "Иванов");
            initClient(7958736239l, "Иван", "Иванов");

        return this;
    }

    public ClientDoc initClient(Long phoneNumber, String firstName, String lastName){
        UserDoc userDoc = userService.findByPhone(phoneNumber);
        try {
            if(userDoc == null) {
                userDoc = new UserDoc();
                userDoc.setPhoneNumber(phoneNumber);
                userDoc.setFirstName(firstName);
                userDoc.setLastName(lastName);
                userService.save(userDoc);
            }
        }catch (Exception ignore){}


        ClientDoc clientDoc = new ClientDoc();
        clientDoc.setId(userDoc.getId());
        clientDoc.setUserId(userDoc.getId());
        clientDoc.setPhoneNumber(phoneNumber);
        clientDoc.setFirstName(firstName);
        clientDoc.setLastName(lastName);
        clientDoc.setbDate("1995-03-15");
        clientService.save(clientDoc);

        return clientDoc;
    }
}
