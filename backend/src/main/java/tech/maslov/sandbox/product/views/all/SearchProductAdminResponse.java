package tech.maslov.sandbox.product.views.all;

import com.ub.core.base.search.SearchResponse;
import tech.maslov.sandbox.product.models.ProductDoc;

import java.util.List;

public class SearchProductAdminResponse extends SearchResponse {
    private List<ProductDoc> result;


    public SearchProductAdminResponse() {
    }

    public SearchProductAdminResponse(Integer currentPage, Integer pageSize, List<ProductDoc> result) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.result = result;
    }

    public List<ProductDoc> getResult() {
        return result;
    }

    public void setResult(List<ProductDoc> result) {
        this.result = result;
    }
}
