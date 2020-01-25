package v1.courier;

import com.ub.core.user.models.UserDoc;
import com.ub.core.user.service.UserService;
import com.ub.core.user.service.exceptions.UserExistException;
import tech.maslov.sandbox.client.services.ClientApiService;
import tech.maslov.sandbox.courier.models.CourierDoc;
import tech.maslov.sandbox.courier.services.CourierApiService;
import tech.maslov.sandbox.courier.services.CourierService;

public class CourierData {
    private CourierService courierService;
    private CourierApiService courierApiService;
    private UserService userService;

    public CourierDoc courierDoc;

    public static CourierData instance(
            CourierService courierService,
            CourierApiService courierApiService,
            UserService userService) {
        CourierData data = new CourierData();
        data.courierService = courierService;
        data.courierApiService = courierApiService;
        data.userService = userService;
        return data;
    }

    public CourierData init() {
        this.courierDoc = initCourier(7958736232l, "Иван", "Иванов");
        return this;
    }

    public CourierDoc initCourier(Long phoneNumber, String firstName, String lastName) {
        UserDoc userDoc = userService.findByPhone(phoneNumber);
        if (userDoc == null) {
            try {
                userDoc = new UserDoc();
                userDoc.setPhoneNumber(phoneNumber);
                userDoc.setFirstName(firstName);
                userDoc.setLastName(lastName);
                userService.save(userDoc);
            } catch (Exception ignore) {
            }
        }


        CourierDoc doc = new CourierDoc();
        doc.setId(userDoc.getId());
        doc.setUserId(userDoc.getId());
        doc.setPhoneNumber(phoneNumber);
        doc.setFirstName(firstName);
        doc.setLastName(lastName);

        courierService.save(doc);

        return doc;
    }
}
