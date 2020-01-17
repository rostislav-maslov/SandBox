package tech.maslov.sandbox.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maslov.sandbox.category.api.response.CategoryApiResponse;
import tech.maslov.sandbox.category.models.CategoryDoc;
import tech.maslov.sandbox.product.api.response.ProductApiResponse;
import tech.maslov.sandbox.product.models.ProductDoc;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductApiService {

    @Autowired private ProductService productService;

    public ProductApiResponse transform(ProductDoc doc){
        ProductApiResponse response = new ProductApiResponse();

        response.setId(doc.getId().toString());
        response.setTitle(doc.getTitle());
        response.setDescription(doc.getDescription());
        response.setPicId(doc.getPicId() != null ? doc.getPicId().toString() : null);
        response.setAvailable(doc.getAvailable());
        response.setCategoryId(doc.getCategoryId() !=null ? doc.getCategoryId().toString() : null);
        response.setPrice(doc.getPrice());

        return response;
    }

    public List<ProductApiResponse> all(){
        List<ProductDoc> products = productService.findAll();

        List<ProductApiResponse> productApiResponses = new ArrayList<>();
        for(ProductDoc productDoc: products){
            productApiResponses.add(transform(productDoc));
        }

        return productApiResponses;
    }
}
