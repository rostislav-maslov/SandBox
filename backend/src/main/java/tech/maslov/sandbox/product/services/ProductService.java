package tech.maslov.sandbox.product.services;

import tech.maslov.sandbox.category.models.CategoryDoc;
import tech.maslov.sandbox.product.models.ProductDoc;
import tech.maslov.sandbox.product.events.IProductEvent;
import tech.maslov.sandbox.product.views.all.SearchProductAdminRequest;
import tech.maslov.sandbox.product.views.all.SearchProductAdminResponse;
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
public class ProductService {
    private static Map<String, IProductEvent> events = new HashMap<String, IProductEvent>();

    @Autowired private MongoTemplate mongoTemplate;

    public static void addEvent(IProductEvent event) {
        events.put(event.getClass().getCanonicalName(), event);
    }

    public ProductDoc save(ProductDoc doc) {
        doc.setUpdateAt(new Date());
        mongoTemplate.save(doc);
        callAfterSave(doc);
        return doc;
    }

    public ProductDoc findById(ObjectId id) {
        return mongoTemplate.findById(id, ProductDoc.class);
    }

    public void remove(ObjectId id) {
        ProductDoc doc = findById(id);
        if (doc == null) return;
        mongoTemplate.remove(doc);
        callAfterDelete(doc);
    }

    public Long count(Query query) {
        return mongoTemplate.count(query, ProductDoc.class);
    }

    public List<ProductDoc> findAll(){
        return mongoTemplate.findAll(ProductDoc.class);
    }

    public SearchProductAdminResponse findAll(SearchProductAdminRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(
                request.getCurrentPage(),
                request.getPageSize(),
                sort);

        Criteria criteria = new Criteria();
        //Criteria.where("title").regex(request.getQuery(), "i");

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, ProductDoc.class);
        query = query.with(pageable);

        List<ProductDoc> result = mongoTemplate.find(query, ProductDoc.class);
        SearchProductAdminResponse response = new SearchProductAdminResponse(
                request.getCurrentPage(),
                request.getPageSize(),
                result);
        response.setAll(count);
        response.setQuery(request.getQuery());
        return response;
    }

    private void callAfterSave(ProductDoc doc) {
        for (IProductEvent event : events.values()) {
            event.afterSave(doc);
        }
    }

    private void callAfterDelete(ProductDoc doc) {
        for (IProductEvent event : events.values()) {
            event.afterDelete(doc);
        }
    }
}
