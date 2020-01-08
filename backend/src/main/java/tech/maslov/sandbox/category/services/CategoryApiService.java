package tech.maslov.sandbox.category.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maslov.sandbox.category.api.response.CategoryApiResponse;
import tech.maslov.sandbox.category.models.CategoryDoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CategoryApiService {
    @Autowired private CategoryService categoryService;

    public   CategoryApiResponse transform(CategoryDoc doc){
        CategoryApiResponse response = new CategoryApiResponse();

        response.setId(doc.getId().toString());
        response.setTitle(doc.getTitle());
        response.setDescription(doc.getDescription());
        response.setPicId(doc.getPicId() != null ? doc.getPicId().toString() : null);
        response.setParentId(doc.getParentId() != null ? doc.getPicId().toString() : null);
        response.setSortNum(doc.getSortNum());
        response.setAvailable(doc.getAvailable());

        return response;
    }

    public List<CategoryApiResponse> all(){
        List<CategoryDoc> categoryDocs = categoryService.findAll();

        List<CategoryApiResponse> result = new ArrayList<CategoryApiResponse>();
        for(CategoryDoc categoryDoc: categoryDocs){
            result.add(transform(categoryDoc));
        }

        return result;
    }

}
