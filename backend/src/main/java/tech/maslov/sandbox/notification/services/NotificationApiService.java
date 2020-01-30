package tech.maslov.sandbox.notification.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maslov.sandbox.notification.api.requests.NotificationCreateRequest;
import tech.maslov.sandbox.notification.api.response.NotificationApiResponse;
import tech.maslov.sandbox.notification.models.NotificationDoc;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationApiService {
    @Autowired private NotificationService notificationService;

    public NotificationApiResponse transform(NotificationDoc notificationDoc){
        NotificationApiResponse notificationApiResponse = new NotificationApiResponse();

        notificationApiResponse.setId(notificationDoc.getId().toString());
        notificationApiResponse.setClientId(notificationDoc.getClientId().toString());
        notificationApiResponse.setOrderId(notificationDoc.getOrderId() != null ? notificationDoc.getOrderId().toString(): null);
        notificationApiResponse.setTitle(notificationDoc.getTitle());
        notificationApiResponse.setDescription(notificationDoc.getDescription());
        notificationApiResponse.setReadStatus(notificationDoc.getReadStatus());
        notificationApiResponse.setCreatedAt(notificationDoc.getCreatedAt());

        return notificationApiResponse;
    }

    public NotificationDoc transform(NotificationCreateRequest request){
        NotificationDoc doc = new NotificationDoc();

        doc.setClientId(request.getClientId());
        doc.setTitle(request.getTitle());
        doc.setDescription(request.getDescription());
        doc.setOrderId(request.getOrderId());

        return doc;
    }

    public NotificationApiResponse create(NotificationCreateRequest request){
        NotificationDoc doc = transform(request);
        notificationService.save(doc);
        return transform(doc);
    }

    public List<NotificationApiResponse> findByClient(ObjectId clientId){
        List<NotificationDoc> notificationDocs = notificationService.findByClient(clientId);
        List<NotificationApiResponse> responses = new ArrayList<>();

        for(NotificationDoc doc : notificationDocs){
            responses.add(transform(doc));
        }

        return responses;
    }
}
