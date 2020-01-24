package tech.maslov.sandbox.client.services;

import com.ub.core.user.models.UserDoc;
import com.ub.core.user.service.UserService;
import com.ub.core.user.service.exceptions.UserExistException;
import com.ub.core.user.service.exceptions.UserNotExistException;
import tech.maslov.sandbox.client.models.ClientDoc;
import tech.maslov.sandbox.client.events.IClientEvent;
import tech.maslov.sandbox.client.roles.ClientRole;
import tech.maslov.sandbox.client.views.all.SearchClientAdminRequest;
import tech.maslov.sandbox.client.views.all.SearchClientAdminResponse;
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
public class ClientService {
    private static Map<String, IClientEvent> events = new HashMap<String, IClientEvent>();

    @Autowired private MongoTemplate mongoTemplate;
    @Autowired private UserService userService;

    public static void addEvent(IClientEvent event) {
        events.put(event.getClass().getCanonicalName(), event);
    }

    public ClientDoc save(ClientDoc doc) {
        doc.setUpdateAt(new Date());
        mongoTemplate.save(doc);
        callAfterSave(doc);
        return doc;
    }

    public ClientDoc findById(ObjectId id) {
        return mongoTemplate.findById(id, ClientDoc.class);
    }

    public ClientDoc create(Long phoneNumber) {
        UserDoc userDoc = userService.findByPhone(phoneNumber);

        if(userDoc == null){
            userDoc = new UserDoc();
            userDoc.setPhoneNumber(phoneNumber);
            userDoc.getRoles().add(new ClientRole());
            try {
                userDoc = userService.save(userDoc);
            } catch (UserExistException ignore) {
            }
        }

        ClientDoc clientDoc = findById(userDoc.getId());

        if(clientDoc == null){
            clientDoc = new ClientDoc();
            clientDoc.setId(userDoc.getId());
            clientDoc.setUserId(userDoc.getId());
            clientDoc.setPhoneNumber(phoneNumber);
            clientDoc = save(clientDoc);

            userDoc.getRoles().add(new ClientRole());
            try {
                userService.save(userDoc);
            } catch (UserExistException e) {
                e.printStackTrace();
            }
        }

        return clientDoc;
    }

    public ClientDoc findByPhoneNumber(Long phoneNumber){
        UserDoc userDoc = userService.findByPhone(phoneNumber);
        if(userDoc == null) return null;

        return findById(userDoc.getId());
    }

    public ClientDoc me(String accessToken){
        UserDoc userDoc = userService.findByAccessToken(accessToken);
        if(userDoc == null) return null;

        return findById(userDoc.getId());
    }

    public void remove(ObjectId id) {
        ClientDoc doc = findById(id);
        if (doc == null) return;
        mongoTemplate.remove(doc);
        callAfterDelete(doc);
    }

    public Long count(Query query) {
        return mongoTemplate.count(query, ClientDoc.class);
    }

    public List<ClientDoc> findAll(Integer size, Integer skip) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Criteria criteria = new Criteria();

        Query query = new Query(criteria);
        query.limit(size);
        query.skip(skip);
        query.with(sort);

        List<ClientDoc> result = mongoTemplate.find(query, ClientDoc.class);
        return result;
    }

    public SearchClientAdminResponse findAll(SearchClientAdminRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(
                request.getCurrentPage(),
                request.getPageSize(),
                sort);

        Criteria criteria = new Criteria();
        //Criteria.where("title").regex(request.getQuery(), "i");

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, ClientDoc.class);
        query = query.with(pageable);

        List<ClientDoc> result = mongoTemplate.find(query, ClientDoc.class);
        SearchClientAdminResponse response = new SearchClientAdminResponse(
                request.getCurrentPage(),
                request.getPageSize(),
                result);
        response.setAll(count);
        response.setQuery(request.getQuery());
        return response;
    }

    private void callAfterSave(ClientDoc doc) {
        for (IClientEvent event : events.values()) {
            event.afterSave(doc);
        }
    }

    private void callAfterDelete(ClientDoc doc) {
        for (IClientEvent event : events.values()) {
            event.afterDelete(doc);
        }
    }
}
