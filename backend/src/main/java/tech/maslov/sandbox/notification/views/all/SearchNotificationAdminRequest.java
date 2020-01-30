package tech.maslov.sandbox.notification.views.all;

import com.ub.core.base.search.SearchRequest;

public class SearchNotificationAdminRequest extends SearchRequest {
    public SearchNotificationAdminRequest() {
    }

    public SearchNotificationAdminRequest(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public SearchNotificationAdminRequest(Integer currentPage, Integer pageSize) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

}
