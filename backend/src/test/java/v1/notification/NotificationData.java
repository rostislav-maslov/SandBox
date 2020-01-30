package v1.notification;

import org.bson.types.ObjectId;
import tech.maslov.sandbox.notification.api.requests.NotificationCreateRequest;
import tech.maslov.sandbox.notification.models.NotificationDoc;
import tech.maslov.sandbox.notification.services.NotificationService;

public class NotificationData {
    private NotificationService notificationService;
    public ObjectId clientId = new ObjectId();
    public ObjectId orderId = new ObjectId();

    public NotificationData(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public static NotificationData instance(NotificationService notificationService){
        return new NotificationData(notificationService);
    }

    public NotificationData init(){
        init(clientId, orderId, "Заказ принят", "Мы приняли ваш заказ");
        init(clientId, orderId, "Заказ готов", "Ваш заказ готов");
        init(clientId, new ObjectId(), "Заказ принят", "Мы приняли ваш заказ");
        init(new ObjectId(), new ObjectId(), "Заказ принят", "Мы приняли ваш заказ");

        return this;
    }

    public NotificationDoc init(ObjectId clientId, ObjectId orderId, String title, String description){
        NotificationDoc doc = new NotificationDoc();

        doc.setOrderId(orderId);
        doc.setClientId(clientId);
        doc.setTitle(title);
        doc.setDescription(description);

        notificationService.save(doc);

        return doc;
    }

    public NotificationCreateRequest request(){
        NotificationCreateRequest request = new NotificationCreateRequest();
        request.setClientId(clientId);
        request.setOrderId(orderId);
        request.setTitle("Заголовок сообщения");
        request.setDescription("Описание сообщения");
        return request;
    }
}
