package tech.maslov.sandbox.client.views.all;

import com.ub.core.base.search.SearchRequest;

public class SearchClientAdminRequest extends SearchRequest {
    public SearchClientAdminRequest() {
    }

    public SearchClientAdminRequest(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public SearchClientAdminRequest(Integer currentPage, Integer pageSize) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

}
