package tech.maslov.sandbox.notification.services;

import tech.maslov.sandbox.notification.models.NotificationDoc;
import tech.maslov.sandbox.notification.events.INotificationEvent;
import tech.maslov.sandbox.notification.views.all.SearchNotificationAdminRequest;
import tech.maslov.sandbox.notification.views.all.SearchNotificationAdminResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.bson.types.ObjectId;
import tech.maslov.sandbox.order.models.OrderDoc;

import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationService {
    private static Map<String, INotificationEvent> events = new HashMap<String, INotificationEvent>();

    @Autowired private MongoTemplate mongoTemplate;

    public static void addEvent(INotificationEvent event) {
        events.put(event.getClass().getCanonicalName(), event);
    }

    public NotificationDoc save(NotificationDoc doc) {
        doc.setUpdateAt(new Date());
        mongoTemplate.save(doc);
        callAfterSave(doc);
        return doc;
    }

    public NotificationDoc findById(ObjectId id) {
        return mongoTemplate.findById(id, NotificationDoc.class);
    }

    public void remove(ObjectId id) {
        NotificationDoc doc = findById(id);
        if (doc == null) return;
        mongoTemplate.remove(doc);
        callAfterDelete(doc);
    }

    public Long count(Query query) {
        return mongoTemplate.count(query, NotificationDoc.class);
    }

    public Long count(ObjectId clientId){
        return mongoTemplate.count(new Query(
                Criteria.where("clientId").is(clientId)
        ), OrderDoc.class);
    }

    public List<NotificationDoc> findByClient(ObjectId clientId){
        return mongoTemplate.find(new Query(
                Criteria.where("clientId").is(clientId)
        ), NotificationDoc.class);
    }

    public SearchNotificationAdminResponse findAll(SearchNotificationAdminRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(
                request.getCurrentPage(),
                request.getPageSize(),
                sort);

        Criteria criteria = new Criteria();
        //Criteria.where("title").regex(request.getQuery(), "i");

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, NotificationDoc.class);
        query = query.with(pageable);

        List<NotificationDoc> result = mongoTemplate.find(query, NotificationDoc.class);
        SearchNotificationAdminResponse response = new SearchNotificationAdminResponse(
                request.getCurrentPage(),
                request.getPageSize(),
                result);
        response.setAll(count);
        response.setQuery(request.getQuery());
        return response;
    }

    private void callAfterSave(NotificationDoc doc) {
        for (INotificationEvent event : events.values()) {
            event.afterSave(doc);
        }
    }

    private void callAfterDelete(NotificationDoc doc) {
        for (INotificationEvent event : events.values()) {
            event.afterDelete(doc);
        }
    }
}
