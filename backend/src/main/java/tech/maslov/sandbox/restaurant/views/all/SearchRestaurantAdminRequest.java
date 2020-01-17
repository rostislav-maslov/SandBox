package tech.maslov.sandbox.restaurant.views.all;

import com.ub.core.base.search.SearchRequest;

public class SearchRestaurantAdminRequest extends SearchRequest {
    public SearchRestaurantAdminRequest() {
    }

    public SearchRestaurantAdminRequest(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public SearchRestaurantAdminRequest(Integer currentPage, Integer pageSize) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

}
