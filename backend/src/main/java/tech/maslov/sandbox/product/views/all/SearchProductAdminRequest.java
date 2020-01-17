package tech.maslov.sandbox.product.views.all;

import com.ub.core.base.search.SearchRequest;

public class SearchProductAdminRequest extends SearchRequest {
    public SearchProductAdminRequest() {
    }

    public SearchProductAdminRequest(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public SearchProductAdminRequest(Integer currentPage, Integer pageSize) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

}
