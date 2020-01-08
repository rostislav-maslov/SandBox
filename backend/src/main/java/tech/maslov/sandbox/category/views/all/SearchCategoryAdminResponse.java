package tech.maslov.sandbox.category.views.all;

import com.ub.core.base.search.SearchResponse;
import tech.maslov.sandbox.category.models.CategoryDoc;

import java.util.List;

public class SearchCategoryAdminResponse extends SearchResponse {
    private List<CategoryDoc> result;


    public SearchCategoryAdminResponse() {
    }

    public SearchCategoryAdminResponse(Integer currentPage, Integer pageSize, List<CategoryDoc> result) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.result = result;
    }

    public List<CategoryDoc> getResult() {
        return result;
    }

    public void setResult(List<CategoryDoc> result) {
        this.result = result;
    }
}
