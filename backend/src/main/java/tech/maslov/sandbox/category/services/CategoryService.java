package tech.maslov.sandbox.category.services;

import tech.maslov.sandbox.category.models.CategoryDoc;
import tech.maslov.sandbox.category.events.ICategoryEvent;
import tech.maslov.sandbox.category.views.all.SearchCategoryAdminRequest;
import tech.maslov.sandbox.category.views.all.SearchCategoryAdminResponse;
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
public class CategoryService {
    private static Map<String, ICategoryEvent> events = new HashMap<String, ICategoryEvent>();

    @Autowired private MongoTemplate mongoTemplate;

    public static void addEvent(ICategoryEvent event) {
        events.put(event.getClass().getCanonicalName(), event);
    }

    public CategoryDoc save(CategoryDoc doc) {
        doc.setUpdateAt(new Date());
        mongoTemplate.save(doc);
        callAfterSave(doc);
        return doc;
    }

    public CategoryDoc findById(ObjectId id) {
        return mongoTemplate.findById(id, CategoryDoc.class);
    }

    public void remove(ObjectId id) {
        CategoryDoc doc = findById(id);
        if (doc == null) return;
        mongoTemplate.remove(doc);
        callAfterDelete(doc);
    }

    public Long count(Query query) {
        return mongoTemplate.count(query, CategoryDoc.class);
    }

    public List<CategoryDoc> findAll(){
        return mongoTemplate.findAll(CategoryDoc.class);
    }

    public Map<ObjectId, CategoryDoc> findHashMap(ObjectId excludeId){
        Map<ObjectId, CategoryDoc> result = findHashMap();
        result.remove(excludeId);
        return result;
    }

    public Map<ObjectId, CategoryDoc> findHashMap(){
        List<CategoryDoc> categoryDocs = findAll();
        Map<ObjectId, CategoryDoc> result = new HashMap<>();
        for(CategoryDoc categoryDoc : categoryDocs){
            result.put(categoryDoc.getId(), categoryDoc);
        }

        return result;
    }

    public SearchCategoryAdminResponse findAll(SearchCategoryAdminRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(
                request.getCurrentPage(),
                request.getPageSize(),
                sort);

        Criteria criteria = new Criteria();
        //Criteria.where("title").regex(request.getQuery(), "i");

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, CategoryDoc.class);
        query = query.with(pageable);

        List<CategoryDoc> result = mongoTemplate.find(query, CategoryDoc.class);
        SearchCategoryAdminResponse response = new SearchCategoryAdminResponse(
                request.getCurrentPage(),
                request.getPageSize(),
                result);
        response.setAll(count);
        response.setQuery(request.getQuery());
        return response;
    }

    private void callAfterSave(CategoryDoc doc) {
        for (ICategoryEvent event : events.values()) {
            event.afterSave(doc);
        }
    }

    private void callAfterDelete(CategoryDoc doc) {
        for (ICategoryEvent event : events.values()) {
            event.afterDelete(doc);
        }
    }
}
