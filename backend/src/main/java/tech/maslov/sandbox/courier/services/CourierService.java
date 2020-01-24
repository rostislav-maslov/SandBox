package tech.maslov.sandbox.courier.services;

import tech.maslov.sandbox.courier.models.CourierDoc;
import tech.maslov.sandbox.courier.events.ICourierEvent;
import tech.maslov.sandbox.courier.views.all.SearchCourierAdminRequest;
import tech.maslov.sandbox.courier.views.all.SearchCourierAdminResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Component
public class CourierService {
    private static Map<String, ICourierEvent> events = new HashMap<String, ICourierEvent>();

    @Autowired private MongoTemplate mongoTemplate;

    public static void addEvent(ICourierEvent event) {
        events.put(event.getClass().getCanonicalName(), event);
    }

    public CourierDoc save(CourierDoc doc) {
        doc.setUpdateAt(new Date());
        mongoTemplate.save(doc);
        callAfterSave(doc);
        return doc;
    }

    public CourierDoc findById(ObjectId id) {
        return mongoTemplate.findById(id, CourierDoc.class);
    }

    public void remove(ObjectId id) {
        CourierDoc doc = findById(id);
        if (doc == null) return;
        mongoTemplate.remove(doc);
        callAfterDelete(doc);
    }

    public Long count(Query query) {
        return mongoTemplate.count(query, CourierDoc.class);
    }

    public SearchCourierAdminResponse findAll(SearchCourierAdminRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(
                request.getCurrentPage(),
                request.getPageSize(),
                sort);

        Criteria criteria = new Criteria();
        //Criteria.where("title").regex(request.getQuery(), "i");

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, CourierDoc.class);
        query = query.with(pageable);

        List<CourierDoc> result = mongoTemplate.find(query, CourierDoc.class);
        SearchCourierAdminResponse response = new SearchCourierAdminResponse(
                request.getCurrentPage(),
                request.getPageSize(),
                result);
        response.setAll(count);
        response.setQuery(request.getQuery());
        return response;
    }

    private void callAfterSave(CourierDoc doc) {
        for (ICourierEvent event : events.values()) {
            event.afterSave(doc);
        }
    }

    private void callAfterDelete(CourierDoc doc) {
        for (ICourierEvent event : events.values()) {
            event.afterDelete(doc);
        }
    }
}
