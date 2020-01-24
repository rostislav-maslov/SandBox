package tech.maslov.sandbox.courier.views.all;

import com.ub.core.base.search.SearchResponse;
import tech.maslov.sandbox.courier.models.CourierDoc;

import java.util.List;

public class SearchCourierAdminResponse extends SearchResponse {
    private List<CourierDoc> result;


    public SearchCourierAdminResponse() {
    }

    public SearchCourierAdminResponse(Integer currentPage, Integer pageSize, List<CourierDoc> result) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.result = result;
    }

    public List<CourierDoc> getResult() {
        return result;
    }

    public void setResult(List<CourierDoc> result) {
        this.result = result;
    }
}
