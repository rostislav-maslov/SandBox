package tech.maslov.sandbox.courier.services;

import com.ub.core.user.models.UserDoc;
import com.ub.core.user.service.UserService;
import com.ub.core.user.service.exceptions.UserExistException;
import tech.maslov.sandbox.courier.models.CourierDoc;
import tech.maslov.sandbox.courier.events.ICourierEvent;
import tech.maslov.sandbox.courier.roles.CourierRole;
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

    @Autowired private UserService userService;
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

    public CourierDoc create(Long phoneNumber, String firstName, String lastName) {
        UserDoc userDoc = userService.findByPhone(phoneNumber);

        if(userDoc == null){
            userDoc = new UserDoc();
            userDoc.setPhoneNumber(phoneNumber);
            userDoc.setFirstName(firstName);
            userDoc.setLastName(lastName);
            try {
                userDoc = userService.save(userDoc);
            } catch (UserExistException ignore) {
            }
        }

        CourierDoc courierDoc = findById(userDoc.getId());

        if(courierDoc == null){
            courierDoc = new CourierDoc();
            courierDoc.setId(userDoc.getId());
            courierDoc.setUserId(userDoc.getId());
            courierDoc.setPhoneNumber(phoneNumber);
            courierDoc.setFirstName(firstName);
            courierDoc.setLastName(lastName);
            courierDoc = save(courierDoc);

            userDoc.getRoles().add(new CourierRole());
            try {
                userService.save(userDoc);
            } catch (UserExistException e) {
                e.printStackTrace();
            }
        }

        return courierDoc;
    }

    public CourierDoc findByPhoneNumber(Long phoneNumber){
        UserDoc userDoc = userService.findByPhone(phoneNumber);
        if(userDoc == null) return null;

        return findById(userDoc.getId());
    }

    public CourierDoc me(String accessToken){
        UserDoc userDoc = userService.findByAccessToken(accessToken);
        if(userDoc == null) return null;

        return findById(userDoc.getId());
    }

    public List<CourierDoc> findAll(Integer size, Integer skip) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Criteria criteria = new Criteria();

        Query query = new Query(criteria);
        query.limit(size);
        query.skip(skip);
        query.with(sort);

        List<CourierDoc> result = mongoTemplate.find(query, CourierDoc.class);
        return result;
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
