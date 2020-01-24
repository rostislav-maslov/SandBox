package tech.maslov.sandbox.client.views.all;

import com.ub.core.base.search.SearchResponse;
import tech.maslov.sandbox.client.models.ClientDoc;

import java.util.List;

public class SearchClientAdminResponse extends SearchResponse {
    private List<ClientDoc> result;


    public SearchClientAdminResponse() {
    }

    public SearchClientAdminResponse(Integer currentPage, Integer pageSize, List<ClientDoc> result) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.result = result;
    }

    public List<ClientDoc> getResult() {
        return result;
    }

    public void setResult(List<ClientDoc> result) {
        this.result = result;
    }
}
