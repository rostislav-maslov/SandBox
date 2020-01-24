package tech.maslov.sandbox.courier.views.all;

import com.ub.core.base.search.SearchRequest;

public class SearchCourierAdminRequest extends SearchRequest {
    public SearchCourierAdminRequest() {
    }

    public SearchCourierAdminRequest(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public SearchCourierAdminRequest(Integer currentPage, Integer pageSize) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

}
