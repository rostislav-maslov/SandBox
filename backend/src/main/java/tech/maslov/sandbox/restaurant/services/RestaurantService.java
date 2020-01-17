package tech.maslov.sandbox.restaurant.services;

import tech.maslov.sandbox.restaurant.models.RestaurantDoc;
import tech.maslov.sandbox.restaurant.events.IRestaurantEvent;
import tech.maslov.sandbox.restaurant.views.all.SearchRestaurantAdminRequest;
import tech.maslov.sandbox.restaurant.views.all.SearchRestaurantAdminResponse;
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
public class RestaurantService {
    private static Map<String, IRestaurantEvent> events = new HashMap<String, IRestaurantEvent>();

    @Autowired private MongoTemplate mongoTemplate;

    public static void addEvent(IRestaurantEvent event) {
        events.put(event.getClass().getCanonicalName(), event);
    }

    public RestaurantDoc save(RestaurantDoc doc) {
        doc.setUpdateAt(new Date());
        mongoTemplate.save(doc);
        callAfterSave(doc);
        return doc;
    }

    public RestaurantDoc findById(ObjectId id) {
        return mongoTemplate.findById(id, RestaurantDoc.class);
    }

    public void remove(ObjectId id) {
        RestaurantDoc doc = findById(id);
        if (doc == null) return;
        mongoTemplate.remove(doc);
        callAfterDelete(doc);
    }

    public Long count(Query query) {
        return mongoTemplate.count(query, RestaurantDoc.class);
    }

    public List<RestaurantDoc> findAll(){
        return mongoTemplate.findAll(RestaurantDoc.class);
    }

    public SearchRestaurantAdminResponse findAll(SearchRestaurantAdminRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(
                request.getCurrentPage(),
                request.getPageSize(),
                sort);

        Criteria criteria = new Criteria();
        //Criteria.where("title").regex(request.getQuery(), "i");

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, RestaurantDoc.class);
        query = query.with(pageable);

        List<RestaurantDoc> result = mongoTemplate.find(query, RestaurantDoc.class);
        SearchRestaurantAdminResponse response = new SearchRestaurantAdminResponse(
                request.getCurrentPage(),
                request.getPageSize(),
                result);
        response.setAll(count);
        response.setQuery(request.getQuery());
        return response;
    }

    private void callAfterSave(RestaurantDoc doc) {
        for (IRestaurantEvent event : events.values()) {
            event.afterSave(doc);
        }
    }

    private void callAfterDelete(RestaurantDoc doc) {
        for (IRestaurantEvent event : events.values()) {
            event.afterDelete(doc);
        }
    }
}
