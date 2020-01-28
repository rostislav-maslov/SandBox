package v1.product;

import org.bson.types.ObjectId;
import tech.maslov.sandbox.product.models.ProductDoc;
import tech.maslov.sandbox.product.services.ProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductData {
    private ProductService productService;

    public List<ProductDoc> productDocs = new ArrayList<>();

    public ProductData(ProductService productService) {
        this.productService = productService;
    }

    public static ProductData instance(ProductService productService) {
        return new ProductData(productService);
    }

    public ProductData init() {
        this.productDocs.add(initProduct("Сет Вкусный", "Состав сета вкусный.", 100.));
        this.productDocs.add(initProduct("Сет Очень Вкусный", "Состав сета очень вкусный.", 200.));
        this.productDocs.add(initProduct("Сет Самый Вкусный", "Состав сета самый вкусный.", 300.));
        return this;
    }

    public ProductDoc initProduct(String title, String description, Double price) {
        ProductDoc doc = new ProductDoc();

        doc.setTitle(title);
        doc.setDescription(description);
        doc.setPicId(new ObjectId());
        doc.setCategoryId(new ObjectId());
        doc.setPrice(price);

        return productService.save(doc);
    }
}
