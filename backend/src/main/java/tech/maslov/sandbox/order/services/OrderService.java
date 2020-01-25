package tech.maslov.sandbox.order.services;

import tech.maslov.sandbox.order.models.DeliveryInfo;
import tech.maslov.sandbox.order.models.OrderDoc;
import tech.maslov.sandbox.order.events.IOrderEvent;
import tech.maslov.sandbox.order.views.all.SearchOrderAdminRequest;
import tech.maslov.sandbox.order.views.all.SearchOrderAdminResponse;
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
public class OrderService {
    private static Map<String, IOrderEvent> events = new HashMap<String, IOrderEvent>();

    @Autowired private MongoTemplate mongoTemplate;

    public static void addEvent(IOrderEvent event) {
        events.put(event.getClass().getCanonicalName(), event);
    }

    public OrderDoc save(OrderDoc doc) {
        if(doc.getNumber() == null){
            doc.setNumber(count(new Query()) + 1);
        }

        doc.setUpdateAt(new Date());
        mongoTemplate.save(doc);
        callAfterSave(doc);
        return doc;
    }

    public OrderDoc findById(ObjectId id) {
        return mongoTemplate.findById(id, OrderDoc.class);
    }

    public void remove(ObjectId id) {
        OrderDoc doc = findById(id);
        if (doc == null) return;
        mongoTemplate.remove(doc);
        callAfterDelete(doc);
    }

    public Long count(ObjectId courierId){
        return mongoTemplate.count(new Query(
                Criteria.where("deliveryInfo.courierId").is(courierId)
        ), OrderDoc.class);
    }

    public Long count(Query query) {
        return mongoTemplate.count(query, OrderDoc.class);
    }

    public List<OrderDoc> findAll(Integer size,  Integer skip){
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);

        query.with(sort);
        query.limit(size);
        query.skip(skip);

        return mongoTemplate.find(query, OrderDoc.class);
    }

    public List<OrderDoc> findAll(ObjectId courierId, Integer size,  Integer skip){
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Criteria criteria = Criteria.where("deliveryInfo.courierId").is(courierId);
        Query query = new Query(criteria);

        query.with(sort);
        query.limit(size);
        query.skip(skip);

        return mongoTemplate.find(query, OrderDoc.class);
    }

    public OrderDoc findNextOrderForCourier(){
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Criteria criteria = Criteria
                .where("deliveryInfo.courierId").is(null)
                .and("deliveryInfo.type").is(DeliveryInfo.TYPE.DELIVERY)
                .and("deliveryInfo.status").is(DeliveryInfo.STATUS.READY);
        Query query = new Query(criteria);

        query.with(sort);
        query.limit(1);

        return mongoTemplate.findOne(query, OrderDoc.class);
    }

    public SearchOrderAdminResponse findAll(SearchOrderAdminRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(
                request.getCurrentPage(),
                request.getPageSize(),
                sort);

        Criteria criteria = new Criteria();
        //Criteria.where("title").regex(request.getQuery(), "i");

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, OrderDoc.class);
        query = query.with(pageable);

        List<OrderDoc> result = mongoTemplate.find(query, OrderDoc.class);
        SearchOrderAdminResponse response = new SearchOrderAdminResponse(
                request.getCurrentPage(),
                request.getPageSize(),
                result);
        response.setAll(count);
        response.setQuery(request.getQuery());
        return response;
    }

    private void callAfterSave(OrderDoc doc) {
        for (IOrderEvent event : events.values()) {
            event.afterSave(doc);
        }
    }

    private void callAfterDelete(OrderDoc doc) {
        for (IOrderEvent event : events.values()) {
            event.afterDelete(doc);
        }
    }
}
