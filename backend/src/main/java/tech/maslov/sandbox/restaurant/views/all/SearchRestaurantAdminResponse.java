package tech.maslov.sandbox.restaurant.views.all;

import com.ub.core.base.search.SearchResponse;
import tech.maslov.sandbox.restaurant.models.RestaurantDoc;

import java.util.List;

public class SearchRestaurantAdminResponse extends SearchResponse {
    private List<RestaurantDoc> result;


    public SearchRestaurantAdminResponse() {
    }

    public SearchRestaurantAdminResponse(Integer currentPage, Integer pageSize, List<RestaurantDoc> result) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.result = result;
    }

    public List<RestaurantDoc> getResult() {
        return result;
    }

    public void setResult(List<RestaurantDoc> result) {
        this.result = result;
    }
}
