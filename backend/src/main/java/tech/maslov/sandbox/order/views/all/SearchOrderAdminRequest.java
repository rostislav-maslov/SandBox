package tech.maslov.sandbox.order.views.all;

import com.ub.core.base.search.SearchRequest;

public class SearchOrderAdminRequest extends SearchRequest {
    public SearchOrderAdminRequest() {
    }

    public SearchOrderAdminRequest(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public SearchOrderAdminRequest(Integer currentPage, Integer pageSize) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

}
