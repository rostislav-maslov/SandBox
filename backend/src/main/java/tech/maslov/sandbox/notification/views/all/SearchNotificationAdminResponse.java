package tech.maslov.sandbox.notification.views.all;

import com.ub.core.base.search.SearchResponse;
import tech.maslov.sandbox.notification.models.NotificationDoc;

import java.util.List;

public class SearchNotificationAdminResponse extends SearchResponse {
    private List<NotificationDoc> result;


    public SearchNotificationAdminResponse() {
    }

    public SearchNotificationAdminResponse(Integer currentPage, Integer pageSize, List<NotificationDoc> result) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.result = result;
    }

    public List<NotificationDoc> getResult() {
        return result;
    }

    public void setResult(List<NotificationDoc> result) {
        this.result = result;
    }
}
