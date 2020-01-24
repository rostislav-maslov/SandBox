package tech.maslov.sandbox.order.models;

import org.bson.types.ObjectId;
import tech.maslov.sandbox.product.models.ProductDoc;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    public static class Product {
        private ObjectId id = new ObjectId();
        private ObjectId productId;
        private ProductDoc productDoc;
        private Integer count;
        private Double totalPrice;

        public ObjectId getId() {
            return id;
        }

        public void setId(ObjectId id) {
            this.id = id;
        }

        public ObjectId getProductId() {
            return productId;
        }

        public void setProductId(ObjectId productId) {
            this.productId = productId;
        }

        public ProductDoc getProductDoc() {
            return productDoc;
        }

        public void setProductDoc(ProductDoc productDoc) {
            this.productDoc = productDoc;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }
    }

    private List<Product> products = new ArrayList<>();
    private Double totalPrice = 0.;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
