package tech.maslov.sandbox.order.views.all;

import com.ub.core.base.search.SearchResponse;
import tech.maslov.sandbox.order.models.OrderDoc;

import java.util.List;

public class SearchOrderAdminResponse extends SearchResponse {
    private List<OrderDoc> result;


    public SearchOrderAdminResponse() {
    }

    public SearchOrderAdminResponse(Integer currentPage, Integer pageSize, List<OrderDoc> result) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.result = result;
    }

    public List<OrderDoc> getResult() {
        return result;
    }

    public void setResult(List<OrderDoc> result) {
        this.result = result;
    }
}
