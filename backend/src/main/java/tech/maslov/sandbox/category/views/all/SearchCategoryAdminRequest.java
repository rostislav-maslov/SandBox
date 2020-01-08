package tech.maslov.sandbox.category.views.all;

import com.ub.core.base.search.SearchRequest;

public class SearchCategoryAdminRequest extends SearchRequest {
    public SearchCategoryAdminRequest() {
    }

    public SearchCategoryAdminRequest(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public SearchCategoryAdminRequest(Integer currentPage, Integer pageSize) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

}
