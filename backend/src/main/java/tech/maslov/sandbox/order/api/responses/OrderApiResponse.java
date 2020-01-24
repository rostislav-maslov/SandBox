package tech.maslov.sandbox.order.api.responses;

import tech.maslov.sandbox.base.models.Point;
import tech.maslov.sandbox.product.api.response.ProductApiResponse;

import java.util.ArrayList;
import java.util.List;

public class OrderApiResponse {
    public static class Product {
        private String id ;
        private String productId;
        private ProductApiResponse product;
        private Integer count;
        private Double totalPrice;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public ProductApiResponse getProduct() {
            return product;
        }

        public void setProduct(ProductApiResponse product) {
            this.product = product;
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
    public static class Basket {
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
    public static class ClientInfo {
        private String clientId;
        private String firstName;
        private Long phoneNumber;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public Long getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(Long phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }
    public static class DeliveryInfo {
        private tech.maslov.sandbox.order.models.DeliveryInfo.TYPE type;
        private String restaurantId;
        private Point currentPosition = new Point();
        private tech.maslov.sandbox.order.models.DeliveryInfo.Address address = new tech.maslov.sandbox.order.models.DeliveryInfo.Address();
        private Double deliveryCost = 0.;

        public tech.maslov.sandbox.order.models.DeliveryInfo.TYPE getType() {
            return type;
        }

        public void setType(tech.maslov.sandbox.order.models.DeliveryInfo.TYPE type) {
            this.type = type;
        }

        public String getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(String restaurantId) {
            this.restaurantId = restaurantId;
        }

        public Point getCurrentPosition() {
            return currentPosition;
        }

        public void setCurrentPosition(Point currentPosition) {
            this.currentPosition = currentPosition;
        }

        public tech.maslov.sandbox.order.models.DeliveryInfo.Address getAddress() {
            return address;
        }

        public void setAddress(tech.maslov.sandbox.order.models.DeliveryInfo.Address address) {
            this.address = address;
        }

        public Double getDeliveryCost() {
            return deliveryCost;
        }

        public void setDeliveryCost(Double deliveryCost) {
            this.deliveryCost = deliveryCost;
        }
    }
    public static class Address{
        private String city;
        private String street;
        private String house;
        private String porch;
        private String floor;
        private String apartment;
        private Point coordinates = new Point();
        private tech.maslov.sandbox.order.models.DeliveryInfo.CoordinateAccuracy coordinateAccuracy = tech.maslov.sandbox.order.models.DeliveryInfo.CoordinateAccuracy.UNDEFINED;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public String getPorch() {
            return porch;
        }

        public void setPorch(String porch) {
            this.porch = porch;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getApartment() {
            return apartment;
        }

        public void setApartment(String apartment) {
            this.apartment = apartment;
        }

        public Point getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Point coordinates) {
            this.coordinates = coordinates;
        }

        public tech.maslov.sandbox.order.models.DeliveryInfo.CoordinateAccuracy getCoordinateAccuracy() {
            return coordinateAccuracy;
        }

        public void setCoordinateAccuracy(tech.maslov.sandbox.order.models.DeliveryInfo.CoordinateAccuracy coordinateAccuracy) {
            this.coordinateAccuracy = coordinateAccuracy;
        }
    }
    public static class OrderInfo {
        private String comment;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
    public static class PaymentInfo {
        private tech.maslov.sandbox.order.models.PaymentInfo.TYPE type;
        private tech.maslov.sandbox.order.models.PaymentInfo.STATUS status = tech.maslov.sandbox.order.models.PaymentInfo.STATUS.NOT_PAYED;

        private Double changeWith = 0.;

        private Double basketCost = 0.;
        private Double deliveryCost = 0.;
        private Double discountCost = 0.;

        private Double total = 0.;

        private String payLink = null;

        public tech.maslov.sandbox.order.models.PaymentInfo.TYPE getType() {
            return type;
        }

        public void setType(tech.maslov.sandbox.order.models.PaymentInfo.TYPE type) {
            this.type = type;
        }

        public tech.maslov.sandbox.order.models.PaymentInfo.STATUS getStatus() {
            return status;
        }

        public void setStatus(tech.maslov.sandbox.order.models.PaymentInfo.STATUS status) {
            this.status = status;
        }

        public Double getBasketCost() {
            return basketCost;
        }

        public void setBasketCost(Double basketCost) {
            this.basketCost = basketCost;
        }

        public Double getDeliveryCost() {
            return deliveryCost;
        }

        public void setDeliveryCost(Double deliveryCost) {
            this.deliveryCost = deliveryCost;
        }

        public Double getDiscountCost() {
            return discountCost;
        }

        public void setDiscountCost(Double discountCost) {
            this.discountCost = discountCost;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Double getChangeWith() {
            return changeWith;
        }

        public void setChangeWith(Double changeWith) {
            this.changeWith = changeWith;
        }

        public String getPayLink() {
            return payLink;
        }

        public void setPayLink(String payLink) {
            this.payLink = payLink;
        }
    }

    private String id;
    private ClientInfo clientInfo = new ClientInfo();
    private Basket basket = new Basket();
    private DeliveryInfo deliveryInfo = new DeliveryInfo();
    private PaymentInfo paymentInfo = new PaymentInfo();
    private OrderInfo orderInfo = new OrderInfo();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
}
